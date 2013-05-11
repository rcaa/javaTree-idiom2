package br.cin.ufpe.features.total;

import java.awt.GridBagConstraints;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.softlang.swing.model.Model;
import org.softlang.swing.view.AbstractView;
import org.softlang.swing.view.CompanyView;
import org.softlang.swing.view.DepartmentView;
import org.softlang.swing.view.EmployeeView;

@Aspect
public class TotalFeature {

	@Pointcut("execution(org.softlang.swing.view.AbstractView.new(..)) && this(cthis)")
	public void newAbstractView(AbstractView cthis) {}
	
	@Around("newAbstractView(cthis)")
	public void around1(AbstractView cthis, ProceedingJoinPoint pjp) throws Throwable {
		// talvez precise do argumento do proceed aqui
		pjp.proceed();
		cthis.total = new JTextField();
	}
	
	@Pointcut("execution(* org.softlang.swing.view.EmployeeView.addSalaryListener(..)) && this(cthis) && args(listener)")
	public void addSalaryListener(EmployeeView cthis, KeyListener listener) {}
	
	@Before("addSalaryListener(cthis, listener)")
	public void before1(EmployeeView cthis, KeyListener listener) {
		cthis.total.addKeyListener(listener);
	}
	
	@Pointcut("call(* org.softlang.swing.view.CompanyView.name(..)) && this(cthis) && withincode(private void org.softlang.swing.view.CompanyView.createView(..))")
	public void createCompanyViewTotal(CompanyView cthis) {}
	
	@After("createCompanyViewTotal(cthis)")
	public void after1(CompanyView cthis) {
		createViewTotal(cthis.c);
		cthis.add(new JLabel("Total: "), cthis.c);
		createTotal(cthis.c, cthis.total, cthis.model);
		cthis.add(cthis.total, cthis.c);
	}
	
	@Pointcut("call(* org.softlang.swing.view.DepartmentView.name(..)) && this(cthis) && withincode(private void org.softlang.swing.view.DepartmentView.createView(..))")
	public void createDepartmentViewTotal(DepartmentView cthis) {}
	
	@AfterReturning("createDepartmentViewTotal(cthis)")
	public void after2(DepartmentView cthis) {
		createViewTotal(cthis.c);
		cthis.add(new JLabel("Total: "), cthis.c);
		createTotal(cthis.c, cthis.total, cthis.model);
		cthis.add(cthis.total, cthis.c);
	}
	
	@Pointcut("call(* org.softlang.swing.view.EmployeeView.salary(..)) && this(cthis) && withincode(private void org.softlang.swing.view.EmployeeView.createView(..))")
	public void createEmployeeViewTotal(EmployeeView cthis) {}
	
	@AfterReturning("createEmployeeViewTotal(cthis)")
	public void after3(EmployeeView cthis) {
		cthis.c.gridx = 1;
		cthis.c.fill = GridBagConstraints.HORIZONTAL;
		cthis.c.weightx = 1;
		cthis.total.setText(cthis.model.getTotal());
		cthis.add(cthis.total, cthis.c);
	}

	private void createViewTotal(GridBagConstraints c) {
		// total
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
	}

	private void createTotal(GridBagConstraints c, JTextField total,
			Model model) {
		c.gridx = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		total.setText(model.getTotal());
		total.setEditable(false);
	}
}

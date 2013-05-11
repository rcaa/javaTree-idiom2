package br.cin.ufpe.features.cut;

import java.awt.GridBagConstraints;
import java.lang.reflect.Field;

import javax.swing.JButton;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.softlang.swing.controller.Controller;
import org.softlang.swing.view.AbstractView;
import org.softlang.swing.view.CompanyView;
import org.softlang.swing.view.DepartmentView;
import org.softlang.swing.view.EmployeeView;

@Aspect
public class CutFeature {

	@Pointcut("execution(org.softlang.swing.view.AbstractView.new(..)) && this(cthis)")
	public void newAbstractView(AbstractView cthis) {}

	@After("newAbstractView(cthis)")
	public void after1(AbstractView cthis) {
		cthis.cut = new JButton("cut");
	}

	@Pointcut("call(org.softlang.swing.view.*View.new(..)) && this(ctl) && withincode(public void org.softlang.swing.controller.Controller.CompaniesTreeListener.valueChanged(..))")
	public void addCutListenerCompanyView(org.softlang.swing.controller.Controller.CompaniesTreeListener ctl) {}
	
	@AfterReturning(value="addCutListenerCompanyView(ctl)", returning="obj")
	public void after2(org.softlang.swing.controller.Controller.CompaniesTreeListener ctl, Object obj) {
		try {
			Field field = org.softlang.swing.controller.Controller.CompaniesTreeListener.class
					.getDeclaredField("this$0");
			field.setAccessible(true);
			Controller outer = (Controller) field.get(ctl);
			if (obj instanceof CompanyView) {
				((CompanyView)obj).addCutListener(CutFeatureInter.aspectOf().new CutListener(outer));
			} else if (obj instanceof DepartmentView) {
				((DepartmentView)obj).addCutListener(CutFeatureInter.aspectOf().new CutListener(outer));
			} else if (obj instanceof EmployeeView) {
				((EmployeeView)obj).addCutListener(CutFeatureInter.aspectOf().new CutListener(outer));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Pointcut("call(* org.softlang.swing.view.CompanyView.filler(..)) && this(cthis) && withincode(private void org.softlang.swing.view.CompanyView.createView(..))")
	public void createCompanyViewCut(CompanyView cthis) {
	}
	
	@Before("createCompanyViewCut(cthis)")
	public void before1(CompanyView cthis) {
		createViewCut(cthis.c, 2);
		cthis.add(cthis.cut, cthis.c);
	}
	
	@Pointcut("call(* org.softlang.swing.view.DepartmentView.filler(..)) && this(cthis) && withincode(private void org.softlang.swing.view.DepartmentView.createView(..))")
	public void createDepartmentViewCut(DepartmentView cthis) {}
	
	@Before("createDepartmentViewCut(cthis)")
	public void before2(DepartmentView cthis) {
		createViewCut(cthis.c, 2);
		cthis.add(cthis.cut, cthis.c);
	}
	
	@Pointcut("call(* org.softlang.swing.view.EmployeeView.filler(..)) && this(cthis) && withincode(private void org.softlang.swing.view.EmployeeView.createView(..))")
	public void createEmployeeViewCut(EmployeeView cthis) {}
	
	@Before("createEmployeeViewCut(cthis)")
	public void before3(EmployeeView cthis) {
		createViewCut(cthis.c, 3);
		cthis.add(cthis.cut, cthis.c);
	}
	
	private void createViewCut(GridBagConstraints c, int gridy) {
		c.gridy = gridy;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
	}
}
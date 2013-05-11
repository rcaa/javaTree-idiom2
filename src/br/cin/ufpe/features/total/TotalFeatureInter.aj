package br.cin.ufpe.features.total;

import javax.swing.JTextField;

import org.softlang.company.Company;
import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.swing.model.Model;
import org.softlang.swing.view.AbstractView;

public privileged aspect TotalFeatureInter {

	/**
	 * This method returns the total value for the current company, department
	 * or employee.
	 * 
	 * @return current total value
	 */
	public String Model.getTotal() {
		if (currentValue != null) {
			if (currentValue.isCompany()) {
				return Double.toString(Total.total((Company) currentValue));
			} else if (currentValue.isDepartment()) {
				return Double.toString(Total.total((Department) currentValue));
			} else if (currentValue.isEmployee()) {
				return Double.toString(Total.total((Employee) currentValue));
			} else {
				return "0";
			}
		} else {
			return "0";
		}
	}

	// changed to public
	public JTextField AbstractView.total;

	// Interesting interaction between Total and Cut
	/**
	 * This method refreshs the total value after a cut.
	 */
	public void AbstractView.refresh() {
		total.setText(model.getTotal());
	}
}

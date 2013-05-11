package br.cin.ufpe.features.cut;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.softlang.company.Company;
import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.swing.controller.Controller;
import org.softlang.swing.model.Model;
import org.softlang.swing.view.AbstractView;
import org.softlang.swing.view.MainView;

public privileged aspect CutFeatureInter {

	public JButton AbstractView.cut;

	/**
	 * This method cuts the current company, department or employee.
	 */
	public void Model.cut() {
		if (currentValue != null) {
			if (currentValue.isCompany()) {
				Cut.cut((Company) currentValue);
			} else if (currentValue.isDepartment()) {
				Cut.cut((Department) currentValue);
			} else if (currentValue.isEmployee()) {
				Cut.cut((Employee) currentValue);
			}
		}
	}

	/**
	 * This method adds the listener for the cut button of the current view.
	 * 
	 * @param cut
	 *            listener
	 */
	public void AbstractView.addCutListener(ActionListener listener) {
		cut.addActionListener(listener);
	}

	/**
	 * refresh GUI for cut operation
	 */
	public void MainView.refresh() {
		((AbstractView)currentView).refresh();
	}

	// had to change
	public class CutListener implements ActionListener {

		private Controller c;

		public CutListener(Controller c) {
			this.c = c;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			c.getModel().cut();
			c.getView().refresh();
		}
	}
}

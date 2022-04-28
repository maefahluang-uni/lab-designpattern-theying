package lab.oodp.company.view;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Optional;

public class EmployeeTableAdapter extends AbstractTableModel {

    private List<Employee> employees;

    public EmployeeTableAdapter(Manager boss) {
        this.employees = boss.getAllEmployees();
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee emp = employees.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return emp.getId();
            case 1:
                return emp.getName();
            case 2:
                return emp.getEmail();
            case 3:
                return emp.getJobTitle();
            case 4:
                return emp.getManager() == null ? "N/A" : emp.getManager().getName();
            case 5:
                return "$" + emp.getSalary();
            default:
                throw new IllegalArgumentException("Unexpected columnIndex");
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Name";
            case 2:
                return "Email address";
            case 3:
                return "Job title";
            case 4:
                return "Reports to";
            case 5:
                return "Salary";
            default:
                throw new IllegalArgumentException("Unexpected column");
        }
    }

    // *** Exercise Four below this point ***


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        Employee emp = employees.get(rowIndex);
        String strValue = aValue.toString();

        switch (columnIndex) {
            case 1:
                emp.setName(strValue);
                break;
            case 2:
                emp.setEmail(strValue);
                break;
            case 3:
                emp.setJobTitle(strValue);
                break;
            case 5:
                try {
                    // Allow user entries that include, or don't include, the dollar sign at the beginning.
                    emp.setSalary(Integer.parseInt(strValue.startsWith("$") ? strValue.substring(1) : strValue));
                } catch (NumberFormatException e) {
                }
                break;
            case 4:

                if ((strValue.isEmpty() || strValue.equalsIgnoreCase("n/a")) && emp.getManager() != null) {
                    // If the user typed no value, or "n/a", remove this employee's manager
                    emp.getManager().removeEmployee(emp);

                } else {
                    // If there's a Manager with the typed name, add this employee to that manager.
                    Optional<Employee> otherEmployee = employees.stream().filter(e -> e.getName().equals(strValue)).findFirst();
                    if (otherEmployee.isPresent() && otherEmployee.get() instanceof Manager) {
                        ((Manager) otherEmployee.get()).addEmployee(emp);
                    }
                }
                break;

        }

    }
}
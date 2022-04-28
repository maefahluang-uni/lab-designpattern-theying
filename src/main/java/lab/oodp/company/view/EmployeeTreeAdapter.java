package lab.oodp.company.view;

import lab.oodp.company.model.Employee;
import lab.oodp.company.model.Manager;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class EmployeeTreeAdapter implements TreeModel {

    private final Employee root;

    public EmployeeTreeAdapter(Employee employee) {
        this.root = employee;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((Manager) parent).getEmployees().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof Manager) {
            return ((Manager) parent).getEmployees().size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isLeaf(Object node) {
        if (node instanceof Manager) {
            return ((Manager) node).getEmployees().size() == 0;
        } else {
            return true;
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Manager) parent).getEmployees().indexOf(child);
    }


    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }
}
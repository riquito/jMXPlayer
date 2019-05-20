package src.Controller;

import src.Model.GraphicInstanceGroup;

public interface PartitureSelectedListener extends java.util.EventListener {
    public void on_partiture_selected(GraphicInstanceGroup group,boolean isSelected);
}
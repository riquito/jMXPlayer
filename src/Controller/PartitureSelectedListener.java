package src.Controller;

import src.Model.MXData;

public interface PartitureSelectedListener extends java.util.EventListener {
    public void on_partiture_selected(MXData.GraphicInstanceGroup group,boolean isSelected);
}
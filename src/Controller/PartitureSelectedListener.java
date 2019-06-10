package src.Controller;

import src.Model.GraphicInstanceGroup;

public interface PartitureSelectedListener extends java.util.EventListener {
    public void onPartitureSelected(GraphicInstanceGroup group,boolean isSelected);
}
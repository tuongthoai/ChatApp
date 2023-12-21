package com.hcmus.ui.table;

import javax.swing.*;

public class RangeSelector extends JSlider {
    // default min = 0, max = 100
    public RangeSelector(){
        initSlider();
    }
    public RangeSelector(int min, int max){
        super(min, max);
        initSlider();
    }

    private void initSlider(){
        setMajorTickSpacing(10);
        setMinorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);
        setOrientation(JSlider.HORIZONTAL);
    }

    @Override
    public void updateUI(){
        setUI(new RangeSelectorUI(this));
        updateLabelUIs();
    }

    @Override
    public void setValue(int value){
        int oldValue = getValue();
        if (oldValue == value) return;
        // Compute new value and extent to maintain upper value.
        int oldExtent = getExtent();
        int newValue = Math.min(Math.max(getMinimum(), value), oldValue + oldExtent);
        int newExtent = oldExtent + oldValue - newValue;

        // Set new value and extent, and fire a single change event.
        getModel().setRangeProperties(newValue, newExtent, getMinimum(), getMaximum(), getValueIsAdjusting());
    }

    public int getUpperValue(){
        return getValue() + getExtent();
    }
    public void setUpperValue(int value){
        int lowerValue = getValue();
        int newExtent = Math.min(Math.max(0, value - lowerValue), getMaximum() - lowerValue);
        setExtent(newExtent);
    }
}

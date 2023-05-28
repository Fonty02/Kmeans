package data;

class ContinuousItem extends Item {
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    double distance(Object a) {
        return Math.abs(((ContinuousAttribute) this.attribute).getScaledValue((Double) value) - ((ContinuousAttribute) this.attribute).getScaledValue((Double) a));
    }

}

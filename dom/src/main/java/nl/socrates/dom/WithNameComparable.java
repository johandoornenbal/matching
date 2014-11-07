package nl.socrates.dom;



public interface WithNameComparable<T extends WithNameComparable<T>> extends Comparable<T>, WithNameGetter {
    
    void setName(String name);
}
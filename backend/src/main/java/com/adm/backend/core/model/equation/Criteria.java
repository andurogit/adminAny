package com.adm.backend.core.model.equation;

import java.util.Objects;
import java.util.Collection;
import java.util.List;

public enum Criteria {
    EQUALS("AO1", "="){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            return Objects.equals(value1, value2);
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    LESS_THAN("AO2", "<"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return Criteria.compare(value1, value2) < 0;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    GREATER_THAN("AO3", ">"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return Criteria.compare(value1, value2) > 0;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    LESS_THAN_EQUALS("AO4", "<="){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return Criteria.compare(value1, value2) <= 0;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    GREATER_THAN_EQUALS("AO5", ">="){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return Criteria.compare(value1, value2) >= 0;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    IN("AO6", "in"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || !(value2 instanceof Collection)) {
                return false;
            }
            Collection<?> options = (Collection<?>) value2;
            for (Object option : options) {
                if (!Objects.equals(value1, option)) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return true;
        }
    }
    ,
    NOT_IN("AO7", "not in"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || !(value2 instanceof Collection)) {
                return false;
            }
            Collection<?> options = (Collection<?>) value2;
            for (Object option : options) {
                if (!Objects.equals(value1, option)) continue;
                return false;
            }
            return true;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return true;
        }
    }
    ,
    BETWEEN("AO8", "between"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || !(value2 instanceof List)) {
                return false;
            }
            List<?> options = (List<?>)value2;
            if (options.size() < 2) {
                return false;
            }
            return Criteria.compare(value1, options.get(0)) > 0 && Criteria.compare(value1, options.get(1)) <= 0;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return true;
        }
    }
    ,
    LIKE("AO9", "like"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return value1.toString().contains(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    NOT_EQUAL("A10", "<>"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            return !Objects.equals(value1, value2);
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    IS_NULL("A11", " is null"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            return value1 == null;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    IS_NOT_NULL("A12", " is not null"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            return value1 != null;
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    COUNT("A13", " count "){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value2 == null || !(value1 instanceof Collection)) {
                return false;
            }
            Collection<?> options = (Collection<?>)value1;
            return options.size() == Integer.parseInt(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return true;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    CONTAINS("A14", " contains "){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value2 == null || !(value1 instanceof Collection)) {
                return false;
            }
            Collection<?> options = (Collection<?>)value1;
            for (Object option : options) {
                if (!Objects.equals(value2, option)) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean isLeftSideCollection() {
            return true;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    COUNT_IS_GREATER_THAN("A15", " count > "){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value2 == null || !(value1 instanceof Collection)) {
                return false;
            }
            Collection<?> items = (Collection<?>)value1;
            return items.size() > Integer.parseInt(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return true;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    COUNT_IS_LESS_THAN("A16", " count < "){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value2 == null || !(value1 instanceof Collection)) {
                return false;
            }
            Collection<?> items = (Collection<?>)value1;
            return items.size() < Integer.parseInt(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return true;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    STARTS_WITH("A17", "starts with"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return value1.toString().startsWith(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    ENDS_WITH("A18", "ends with"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return value1.toString().endsWith(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    NOT_EQUALS("A19", "!="){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            return !Objects.equals(value1, value2);
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    }
    ,
    CONTAINS_STRING("A20", "contains string"){

        @Override
        public boolean evaluate(Object value1, Object value2) {
            if (value1 == null || value2 == null) {
                return false;
            }
            return value1.toString().contains(value2.toString());
        }

        @Override
        public boolean isLeftSideCollection() {
            return false;
        }

        @Override
        public boolean isRightSideCollection() {
            return false;
        }
    };
    
    private String code;
    private String expression;

    private Criteria(String code, String expression) {
        this.code = code;
        this.expression = expression;
    }

    public String getCode() {
        return this.code;
    }

    public String getExpression() {
        return this.expression;
    }

    public abstract boolean isLeftSideCollection();

    public abstract boolean isRightSideCollection();

    public abstract boolean evaluate(Object var1, Object var2);

    @SuppressWarnings("unchecked")
    private static int compare(Object value1, Object value2) {
        Object c2;
        Object c1;
        if (value1 instanceof Number && value2 instanceof Number) {
            if (!value1.getClass().equals(value2.getClass())) {
                c1 = ((Number)value1).doubleValue();
                c2 = ((Number)value2).doubleValue();
            } else {
                c1 = (Comparable<?>)value1;
                c2 = (Comparable<?>)value2;
            }
        } else if (value1 instanceof Comparable && value2 instanceof Comparable) {
            c1 = (Comparable<?>)value1;
            c2 = (Comparable<?>)value2;
        } else {
            c1 = value1.toString();
            c2 = value2.toString();
        }
        return ((Comparable<Object>) c1).compareTo(c2);
    }
}
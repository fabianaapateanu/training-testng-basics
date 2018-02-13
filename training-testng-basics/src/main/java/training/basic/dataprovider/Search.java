package training.basic.dataprovider;

public class Search {

    private Object[] searchString;

    public Search(){
        this.searchString = new Object[]{"test", "provider", "requests"};
    }

    public Object[] getSearchString() {
        return searchString;
    }
}

package application;



public abstract class LibraryItem {
    public String title;
    public String type;
    public int yearPublished;
    public int pageQuantity;

    public LibraryItem(String title, String type, int yearPublished, int pageQuantity) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.pageQuantity = pageQuantity;
        this.type = type;
    }

    // Detayları döndüren soyut metod
    public abstract String getDetails();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	public int getPageQuantity() {
		return pageQuantity;
	}

	public void setPageQuantity(int pageQuantity) {
		this.pageQuantity = pageQuantity;
	}
}
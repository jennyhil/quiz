package is.hi.quiz.Persistance.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String categoryName;
    private int categoryID;
    @OneToMany(mappedBy = "categoryID",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Question> questions= new ArrayList<>();

    public Category(int categoryID, String categoryName) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public Category() {

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}

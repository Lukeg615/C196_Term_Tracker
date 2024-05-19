package entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "course", foreignKeys = @ForeignKey(entity = Term.class, parentColumns = "termID", childColumns = "termId", onDelete = ForeignKey.CASCADE))
public class Course {

    @PrimaryKey(autoGenerate = true)
    public Integer courseID;
    @ColumnInfo(name = "course_name")
    public String courseName;
    @ColumnInfo (name = "start_date")
    public LocalDate startDate;
    @ColumnInfo (name = "end_date")
    public LocalDate endDate;

    // Foreign key referencing the term table
    @ColumnInfo(index = true)
    public int termId;

    public Course(Integer courseID, String courseName, LocalDate startDate, LocalDate endDate, int termId) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.termId = termId;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setCourseName(String termName) {
        this.courseName = courseName;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                ", startDate=" + startDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
                ", endDate=" + endDate.format(DateTimeFormatter.ISO_LOCAL_DATE) +
                ", termId=" + termId +
                '}';
    }
}

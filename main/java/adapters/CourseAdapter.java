package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.c196_term_tracker.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

import entities.Course;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private final List<Course> courseList;
    private final Context context;
    private final OnCourseListener mOnCourseListener;
    DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;

    public CourseAdapter(Context context, List<Course> courseList, OnCourseListener mOnCourseListener) {
        this.context = context;
        this.courseList = courseList;
        this.mOnCourseListener = mOnCourseListener;

    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView CourseName;
        private final TextView StartDate;
        private final TextView EndDate;
        OnCourseListener onCourseListener;

        public CourseViewHolder(View view, OnCourseListener onCourseListener) {
            super(view);
            CourseName=itemView.findViewById(R.id.courseName);
            StartDate=itemView.findViewById(R.id.courseStartDate);
            EndDate=itemView.findViewById(R.id.courseEndDate);
            this.onCourseListener = onCourseListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCourseListener.onCourseClick(getAbsoluteAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_list, parent, false);

        return new CourseViewHolder(view, mOnCourseListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        holder.CourseName.setText(this.courseList.get(position).courseName);
        holder.StartDate.setText(this.courseList.get(position).startDate.format(format));
        holder.EndDate.setText(this.courseList.get(position).endDate.format(format));
    }

    @Override
    public int getItemCount() {
        if (courseList!=null) {
            return courseList.size();
        }
        else return 0;
    }

    public interface OnCourseListener{
        void onCourseClick(int position);
    }

}

package com.dahai.mvpdemo.google.tasks;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dahai.mvpdemo.R;
import com.dahai.mvpdemo.google.data.Task;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment implements TasksContract.View{
    private View rootView;
    private TasksContract.Presenter mPresenter;
    private TasksAdapter mListAdapter;
    private View mNoTasksView;
    private ImageView mNoTaskIcon;
    private TextView mNoTaskMainView;
    private TextView mNoTaskAddView;
    private LinearLayout mTasksView;
    private TextView mFilteringLabelView;


    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(){
        return new TasksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new TasksAdapter(new ArrayList<Task>(0),mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_blank,container,false);

        // set up tasks view
        ListView listView = (ListView) rootView.findViewById(R.id.tasks_list);
        listView.setAdapter(mListAdapter);
        mFilteringLabelView = (TextView) rootView.findViewById(R.id.filteringLabel);
        mTasksView = (LinearLayout) rootView.findViewById(R.id.tasksLL);

        // set up no tasks view
        mNoTasksView = rootView.findViewById(R.id.noTasks);
        mNoTaskIcon = (ImageView) rootView.findViewById(R.id.noTasksIcon);
        mNoTaskMainView = (TextView) rootView.findViewById(R.id.noTasksMain);
        mNoTaskAddView = (TextView) rootView.findViewById(R.id.noTasksAdd);
        mNoTaskAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTask();
            }
        });

        // set up floating action button
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewTask();
            }
        });
        // set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = (ScrollChildSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(),R.color.colorPrimary),
                ContextCompat.getColor(getActivity(),R.color.colorAccent),
                ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark)
        );
        // set the scrolling view in the custom swipeRefreshLayout.
        swipeRefreshLayout.setScrollUPChild(listView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadTasks(false);
            }
        });

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.menu_clear:
                break;*/
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode,resultCode);
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTasks(List<Task> tasks) {

    }

    @Override
    public void showAddTask() {

    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showTaskMarkedComplete() {

    }

    @Override
    public void showTaskMarkedActive() {

    }

    @Override
    public void showCompletedTasksCleared() {

    }

    @Override
    public void showLoadingTaskError() {

    }

    @Override
    public void showNoTasks() {

    }

    @Override
    public void showActiveFilterLabel() {

    }

    @Override
    public void showCompletedFilterLabel() {

    }

    @Override
    public void showAllFilterLabel() {

    }

    @Override
    public void showNoActiveTasks() {

    }

    @Override
    public void showNoCompletedTasks() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    private static class TasksAdapter extends BaseAdapter {
        private List<Task> mTasks;
        private TaskItemListener mItemListener;

        public TasksAdapter(List<Task> tasks,TaskItemListener itemListener) {
            setList(tasks);
            mItemListener = itemListener;
        }

        public void replaceData(List<Task> tasks) {
            setList(tasks);
            notifyDataSetChanged();
        }

        private void setList(List<Task> tasks) {
            mTasks = checkNotNull(tasks);
        }

        @Override
        public int getCount() {
            return mTasks.size();
        }

        @Override
        public Object getItem(int position) {
            return mTasks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                rowView = inflater.inflate(R.layout.task_item,parent,false);
            }
            final Task task = (Task) getItem(position);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText(task.getTitle());
            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);

            // Active/completed task UI
            if (task.isCompleted()) {
                rowView.setBackgroundDrawable(parent.getContext().getResources().getDrawable(R.drawable.list_completed_touch_feedback));
            } else {
                rowView.setBackgroundDrawable(parent.getContext().getResources().getDrawable(R.drawable.touch_feedback));
            }

            completeCB.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (!task.isCompleted()) {
                        mItemListener.onCompleteTaskClick(task);
                    } else {
                        mItemListener.onActivateTaskClick(task);
                    }
                }
            });

            rowView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mItemListener.onTaskClick(task);
                }
            });

            return rowView;
        }
    }

    public interface TaskItemListener {
        void onTaskClick(Task clickedTask);
        void onCompleteTaskClick(Task completedTask);
        void onActivateTaskClick(Task activatedTask);
    }

    /**
     *  Listener for clicks on tasks in the ListView.
     */
    TaskItemListener mItemListener = new TaskItemListener() {
        @Override
        public void onTaskClick(Task clickedTask) {
            mPresenter.openTaskDetails(clickedTask);
        }

        @Override
        public void onCompleteTaskClick(Task completedTask) {
            mPresenter.completeTask(completedTask);
        }

        @Override
        public void onActivateTaskClick(Task activatedTask) {
            mPresenter.activateTask(activatedTask);
        }
    };
}

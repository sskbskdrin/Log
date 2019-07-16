package cn.sskbskdrin.log.widget;

import android.app.Activity;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import cn.sskbskdrin.log.Printer;
import cn.sskbskdrin.log.R;

/**
 * Created by ex-keayuan001 on 2019-07-05.
 *
 * @author ex-keayuan001
 */
public class LogWidget {
    private static final String[] LEVEL = {"Verbose", "Debug", "Info", "Warn", "Error", "Assert"};

    private static LogWidget instance;

    private LogWidget() {}

    private static LogWidget getInstance() {
        if (instance == null) {
            instance = new LogWidget();
        }
        return instance;
    }

    private View root;
    private ListView listView;
    private Log[] mList;
    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mList == null ? 0 : mList.length;
        }

        @Override
        public Log getItem(int position) {
            return mList == null ? null : mList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = new TextView(root.getContext());
                ((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
            Log log = getItem(position);
            if (log != null) {
                TextView view = (TextView) convertView;
                view.setText(log.getContent());
                view.setTextColor(log.color());
            }
            return convertView;
        }
    };

    private LogCache printer = new LogCache() {
        @Override
        protected void onRefresh(Log[] list) {
            mList = list;
            adapter.notifyDataSetChanged();
            listView.setSelection(list.length - 1);
        }
    };

    public static void setCacheSize(int size) {
        getInstance().printer.setCacheMax(size);
    }

    public static void attach(Activity activity) {
        getInstance().init(activity);
    }

    private void init(Activity activity) {
        if (root == null) {
            root = View.inflate(activity, R.layout.log_layout, null);
            listView = root.findViewById(R.id.log_list);
            listView.setAdapter(adapter);

            root.findViewById(R.id.log_open).setOnTouchListener(new View.OnTouchListener() {
                PointF last = new PointF();
                PointF move = new PointF();
                PointF parentSize = null;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (parentSize == null) {
                                parentSize = new PointF(((View) v.getParent()).getWidth(),
                                    ((View) v.getParent()).getHeight());
                            }
                            last = new PointF(event.getX(), event.getY());
                            move.set(0, 0);
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            move.x += event.getX() - last.x;
                            move.y += event.getY() - last.y;
                            float newX = v.getX() + event.getX() - last.x;
                            float newY = v.getY() + event.getY() - last.y;

                            if (newX > 0 && newX < parentSize.x - v.getWidth()) {
                                v.setX(newX);
                            }
                            if (newY > 0 && newY < parentSize.y - v.getHeight()) {
                                v.setY(newY);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if (v.getX() < parentSize.x / 2) {
                                v.setX(0);
                            } else {
                                v.setX(parentSize.x - v.getWidth());
                            }
                            if (Math.abs(move.x) < 5 || Math.abs(move.y) < 5) {
                                int show = root.findViewById(R.id.log_content).getVisibility();
                                root.findViewById(R.id.log_content).setVisibility(show == View.VISIBLE ? View.GONE :
                                    View.VISIBLE);
                            }
                        default:
                            last = null;
                    }
                    return true;
                }
            });
            Drawable drawable = new ShapeDrawable(new ArcShape(0, 360));
            drawable.setColorFilter(new PorterDuffColorFilter(0xa000b5ff, PorterDuff.Mode.SRC_IN));
            root.findViewById(R.id.log_open).setBackgroundDrawable(drawable);

            Spinner spinner = root.findViewById(R.id.log_level);
            spinner.setAdapter(new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, LEVEL));
            spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    printer.setLevel(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ((EditText) root.findViewById(R.id.log_filter)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    printer.setFilter(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            root.findViewById(R.id.log_clear).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    printer.clear();
                }
            });
        }
        if (root.getParent() != null) {
            ((ViewGroup) root.getParent()).removeView(root);
        }
        ViewGroup group = activity.findViewById(android.R.id.content);
        group.addView(root);
    }

    public static void detach() {
        if (getInstance().root != null && getInstance().root.getParent() != null) {
            ((ViewGroup) getInstance().root.getParent()).removeView(getInstance().root);
        }
    }

    public static Printer getPrinter() {
        return getInstance().printer;
    }
}

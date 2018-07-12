package com.penny.pwdedit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pwdedit.R;

public class PwdEditText extends ConstraintLayout implements TextWatcher {

  private View.OnFocusChangeListener onFocusChangeListener;
  private EditText editText;
  private Drawable clearIcon;
  private TextView pwdStrentghTv;
  private ImageView visibleIv;
  private ImageView clearPwdIv;
  private TextWatcher textWatcher;

  private String hinttxt;
  private ColorStateList textcolor;
  private ColorStateList textHintcolor;
  private float textSize;

  public PwdEditText(Context context) {
    this(context, null);
  }

  public PwdEditText(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public Editable getText() {
    return editText.getText();
  }

  public EditText getEditText() {
    return editText;
  }

  private void init(final Context context, final AttributeSet attrs) {
    LayoutInflater.from(context).inflate(R.layout.layout_pwd_edit, this);

    if (attrs != null) {
      TypedArray typedArray =
          context.obtainStyledAttributes(attrs, R.styleable.PwdEditText);
      hinttxt = typedArray.getString(R.styleable.PwdEditText_pwd_hint_txt);
      textcolor = typedArray.getColorStateList(R.styleable.PwdEditText_pwd_txt_color);
      textHintcolor = typedArray.getColorStateList(R.styleable.PwdEditText_pwd_hint_color);
      textSize = typedArray.getDimensionPixelSize(R.styleable.PwdEditText_pwd_txt_size, 51);
      typedArray.recycle();
    }
    if (clearIcon == null) {
      clearIcon = context.getResources().getDrawable(R.drawable.ic_clear);
    }
    clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
    editText = findViewById(R.id.et_pwdEdit);
    clearPwdIv = findViewById(R.id.iv_pwd_clear);
    visibleIv = findViewById(R.id.iv_pwd_visiable);
    pwdStrentghTv = findViewById(R.id.tv_pwd_strength);
    editText.setHint(hinttxt);
    if (textcolor != null) {
      editText.setTextColor(textcolor);
    }
    if (textHintcolor != null) {
      editText.setHintTextColor(textHintcolor);
    }
    editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
          setClearIconVisible(editText.getText().toString().length() > 0);
        } else {
          setClearIconVisible(false);
        }
        if (onFocusChangeListener != null) {
          onFocusChangeListener.onFocusChange(v, hasFocus);
        }
      }
    });
    clearPwdIv.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editText.setText("");
      }
    });
    editText.addTextChangedListener(this);
    visibleIv.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        boolean shouldShowPassword =
            editText.getTransformationMethod() == PasswordTransformationMethod.getInstance();
        TransformationMethod method =
            shouldShowPassword ? HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance();
        int resId =
            shouldShowPassword ? R.drawable.ic_password_visible : R.drawable.ic_password_invisible;
        visibleIv.setImageResource(resId);
        editText.setTransformationMethod(method);
        editText.setSelection(editText.getText().length());
      }
    });
  }

  private void updateVisibleState() {
    if (TextUtils.isEmpty(editText.getText().toString())) {
      visibleIv.setEnabled(false);
      visibleIv.setVisibility(INVISIBLE);
    } else {
      visibleIv.setEnabled(true);
      visibleIv.setVisibility(VISIBLE);
    }
  }

  protected void setClearIconVisible(boolean visible) {
    clearPwdIv.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
  }

  @Override public void setOnFocusChangeListener(View.OnFocusChangeListener l) {
    this.onFocusChangeListener = l;
  }

  public void setTextWatcher(TextWatcher textWatcher) {
    this.textWatcher = textWatcher;
  }

  @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
    updatePwdLevel(PwdStrengthGradeUtils.calculateGrade(s.toString().trim()));
    updateVisibleState();
    if (editText.isFocused()) {
      setClearIconVisible(s.length() > 0);
    }
    if (textWatcher != null) {
      textWatcher.onTextChanged(s, start, before, count);
    }
  }

  @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override public void afterTextChanged(Editable s) {

  }

  private void updatePwdLevel(int level) {
    switch (level) {
      case PwdStrengthGradeUtils.WEEK_LEVEL:
        pwdStrentghTv.setText(R.string.pwd_strength_weak_tip);
        pwdStrentghTv.setBackgroundResource(R.drawable.bg_pwd_weak);
        pwdStrentghTv.setVisibility(View.VISIBLE);
        break;
      case PwdStrengthGradeUtils.MIDDLE_LEVEL:
        pwdStrentghTv.setText(R.string.pwd_strength_middle_tip);
        pwdStrentghTv.setBackgroundResource(R.drawable.bg_pwd_middle);
        pwdStrentghTv.setVisibility(View.VISIBLE);
        break;
      case PwdStrengthGradeUtils.STRONG_LEVEL:
        pwdStrentghTv.setText(R.string.pwd_strength_strong_tip);
        pwdStrentghTv.setBackgroundResource(R.drawable.bg_pwd_strong);
        pwdStrentghTv.setVisibility(View.VISIBLE);
        break;
      case PwdStrengthGradeUtils.WEEK_NONE:
      default:
        pwdStrentghTv.setVisibility(View.INVISIBLE);
        break;
    }
  }
}

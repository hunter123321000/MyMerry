package com.hunter123321000.merry;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TextViewVertical extends View {

	public static final int LAYOUT_CHANGED = 1;
    private Paint paint;
    private int mTextPosx = 0;// x?��?
    private int mTextPosy = 0;// y?��?
    private int mTextWidth = 0;// 绘制宽度
    private int mTextHeight = 0;// 绘制高度
    private int mFontHeight = 0;// 绘制字�?高度
    private float mFontSize = 24;// 字�?大�?
    private int mRealLine = 0;// 字符串�?实�?行数
    private int mLineWidth = 0;//?�宽�?
    private int TextLength = 0 ;//字符串长�?
    private int oldwidth = 0 ;//存储久�?width
    private String text="";//待显示�??��?
    private Handler mHandler=null;
    private Matrix matrix;
    BitmapDrawable drawable = (BitmapDrawable) getBackground();

    public TextViewVertical(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
    public TextViewVertical(Context context,AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        paint = new Paint();//?�建?��?
        paint.setTextAlign(Align.CENTER);//?��?居中
        paint.setAntiAlias(true);//平�?处�?
        paint.setColor(Color.BLUE);//默认?��?颜色
        try{
        	mFontSize	= Float.parseFloat(attrs.getAttributeValue(null,"textSize"));//?��?字�?大�?属�?  	
        }catch(Exception e){}
    }
    /*
    //?��??�数??
    private final int getAttributeIntValue(AttributeSet attrs,String field) {
    	int intVal = 0;
    	//TODO 
    	//应该?�以?�接?�attrs.getAttributeIntValue()?��?对�??�数?��?�?
    	//但�??��?为�?么�??��?法获得只好临?��?个函?��??��???没�??��??��??�时?�支?�px作为?��?，其它�?位�?转换?�空?��?
    	String tempText=attrs.getAttributeValue(androidns, field);
    	intVal = (int)Math.ceil(Float.parseFloat(tempText.replaceAll("px","")));
		return intVal;
    }*/
    //设置?��?
    public final void setText(String text) {
    	this.text=text;
    	this.TextLength = text.length();
    	if(mTextHeight>0)
    		GetTextInfo();
    }
    //设置字�?大�?
    public final void setTextSize(float size) {
        if (size != paint.getTextSize()) {
        	mFontSize = size;
        	if(mTextHeight>0)
        		GetTextInfo();
        }
    }
    //设置字�?颜色
    public final void setTextColor(int color) {
    	paint.setColor(color);
    }
    //设置字�?颜色
    public final void setTextARGB(int a,int r,int g,int b) {
    	paint.setARGB(a, r, g, b);
    }
    //设置字�?
    public void setTypeface(Typeface tf) {
        if (this.paint.getTypeface() != tf) {
        	this.paint.setTypeface(tf);
        }
    }
    //设置行宽
    public void setLineWidth(int LineWidth) {
    	mLineWidth = LineWidth;
    }
    //?��?实�?宽度
    public int getTextWidth() {
		return mTextWidth;
    }
    //设置Handler，用以�??��?�?
    public void setHandler(Handler handler) {
    	mHandler=handler;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	Log.v("TextViewVertical","onDraw");
    	if(drawable != null){
    		//?��???
    		Bitmap b = Bitmap.createBitmap(drawable.getBitmap(),0,0,mTextWidth,mTextHeight);
    		canvas.drawBitmap(b, matrix, paint);
    	}
        //?��?
        draw(canvas, this.text);
    } 
    
    private void draw(Canvas canvas, String thetext) {
    	char ch;
    	mTextPosy = 0;//?��??�y?��?
    	mTextPosx = mTextWidth - mLineWidth;//?��??�x?��?    	
        for (int i = 0; i < this.TextLength; i++) {
        	ch = thetext.charAt(i);
        	if (ch == '\n') {
        		mTextPosx -= mLineWidth;// ?��?
    	    	mTextPosy = 0;
        	} else {
        		mTextPosy += mFontHeight;
        		if (mTextPosy > this.mTextHeight) {
        			mTextPosx -= mLineWidth;// ?��?
        			i--;
        			mTextPosy = 0;
        		}else{
        			canvas.drawText(String.valueOf(ch), mTextPosx, mTextPosy, paint);
        		}
        	}	
        }
    }
    //计�??��?行数?��?�?
    private void GetTextInfo() {
    	Log.v("TextViewVertical","GetTextInfo");
    	char ch;
    	int h = 0;
    	paint.setTextSize(mFontSize);
    	//?��?字宽
    	if(mLineWidth == 0){
    		float[] widths = new float[1];
    		paint.getTextWidths("��", widths);//?��??�个汉�??�宽�?
    		mLineWidth = (int) Math.ceil(widths[0] * 1.1 +2);
    	}
    	
    	FontMetrics fm = paint.getFontMetrics();  
      	mFontHeight = (int) (Math.ceil(fm.descent - fm.top) * 1.0);// ?��?字�?高度
      	
      	//计�??��?行数
      	mRealLine=0;
    	for (int i = 0; i < this.TextLength; i++) {
    		ch = this.text.charAt(i);
    		if (ch == '\n') {
	    	    mRealLine++;// ?��??��??��?�?
	    	    h = 0;
    		} else {
    			h += mFontHeight;
    			if (h > this.mTextHeight) {
    				mRealLine++;// ?��??��??��?�?
    				i--;
    				h = 0;
    			} else {
    				if (i == this.TextLength - 1) {
    					mRealLine++;// ?��??��??��?�?
    				}
    			}
    	   }
    	}
    	mRealLine++;//额�?增�?�??
    	mTextWidth = mLineWidth*mRealLine;//计�??��??�宽�?
    	measure(mTextWidth, getHeight());//?�新调整大�?
        layout(getLeft(), getTop(), getLeft()+mTextWidth, getBottom());//?�新绘制容器
    }
    
    @Override   
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
		int measuredHeight = measureHeight(heightMeasureSpec);    
		if(mTextWidth==0)
			GetTextInfo();
		setMeasuredDimension(mTextWidth, measuredHeight);  
		if(oldwidth!=getWidth()) {
			oldwidth=getWidth();
			if(mHandler!=null)mHandler.sendEmptyMessage(LAYOUT_CHANGED);
		}
	}  
		  
	private int measureHeight(int measureSpec) {  
		int specMode = MeasureSpec.getMode(measureSpec);  
		int specSize = MeasureSpec.getSize(measureSpec);  
		int result = 500;  
		if (specMode == MeasureSpec.AT_MOST) {  
			result = specSize;  
		}else if (specMode == MeasureSpec.EXACTLY) {  
			result = specSize;  
		}  
		mTextHeight=result;//设置?�本高度
		return result;  
	}  
}
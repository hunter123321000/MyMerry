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
    private int mTextPosx = 0;// x?æ?
    private int mTextPosy = 0;// y?æ?
    private int mTextWidth = 0;// ç»˜åˆ¶å®½åº¦
    private int mTextHeight = 0;// ç»˜åˆ¶é«˜åº¦
    private int mFontHeight = 0;// ç»˜åˆ¶å­—ä?é«˜åº¦
    private float mFontSize = 24;// å­—ä?å¤§å?
    private int mRealLine = 0;// å­—ç¬¦ä¸²ç?å®ç?è¡Œæ•°
    private int mLineWidth = 0;//?—å®½åº?
    private int TextLength = 0 ;//å­—ç¬¦ä¸²é•¿åº?
    private int oldwidth = 0 ;//å­˜å‚¨ä¹…ç?width
    private String text="";//å¾…æ˜¾ç¤ºç??‡å?
    private Handler mHandler=null;
    private Matrix matrix;
    BitmapDrawable drawable = (BitmapDrawable) getBackground();

    public TextViewVertical(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
    public TextViewVertical(Context context,AttributeSet attrs) {
        super(context, attrs);
        matrix = new Matrix();
        paint = new Paint();//?°å»º?»ç?
        paint.setTextAlign(Align.CENTER);//?‡å?å±…ä¸­
        paint.setAntiAlias(true);//å¹³æ?å¤„ç?
        paint.setColor(Color.BLUE);//é»˜è®¤?‡å?é¢œè‰²
        try{
        	mFontSize	= Float.parseFloat(attrs.getAttributeValue(null,"textSize"));//?·å?å­—ä?å¤§å?å±æ?  	
        }catch(Exception e){}
    }
    /*
    //?·å??´æ•°??
    private final int getAttributeIntValue(AttributeSet attrs,String field) {
    	int intVal = 0;
    	//TODO 
    	//åº”è¯¥?¯ä»¥?´æ¥?¨attrs.getAttributeIntValue()?·å?å¯¹å??„æ•°?¼ç?ï¼?
    	//ä½†ä??¥é?ä¸ºä?ä¹ˆä??´æ?æ³•è·å¾—åªå¥½ä¸´?¶å?ä¸ªå‡½?°å??ˆç???æ²¡æ??™å??´ï??‚æ—¶?ªæ”¯?pxä½œä¸º?•ä?ï¼Œå…¶å®ƒå?ä½ç?è½¬æ¢?‰ç©º?å?
    	String tempText=attrs.getAttributeValue(androidns, field);
    	intVal = (int)Math.ceil(Float.parseFloat(tempText.replaceAll("px","")));
		return intVal;
    }*/
    //è®¾ç½®?‡å?
    public final void setText(String text) {
    	this.text=text;
    	this.TextLength = text.length();
    	if(mTextHeight>0)
    		GetTextInfo();
    }
    //è®¾ç½®å­—ä?å¤§å?
    public final void setTextSize(float size) {
        if (size != paint.getTextSize()) {
        	mFontSize = size;
        	if(mTextHeight>0)
        		GetTextInfo();
        }
    }
    //è®¾ç½®å­—ä?é¢œè‰²
    public final void setTextColor(int color) {
    	paint.setColor(color);
    }
    //è®¾ç½®å­—ä?é¢œè‰²
    public final void setTextARGB(int a,int r,int g,int b) {
    	paint.setARGB(a, r, g, b);
    }
    //è®¾ç½®å­—ä?
    public void setTypeface(Typeface tf) {
        if (this.paint.getTypeface() != tf) {
        	this.paint.setTypeface(tf);
        }
    }
    //è®¾ç½®è¡Œå®½
    public void setLineWidth(int LineWidth) {
    	mLineWidth = LineWidth;
    }
    //?·å?å®é?å®½åº¦
    public int getTextWidth() {
		return mTextWidth;
    }
    //è®¾ç½®Handlerï¼Œç”¨ä»¥å??ä?ä»?
    public void setHandler(Handler handler) {
    	mHandler=handler;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	Log.v("TextViewVertical","onDraw");
    	if(drawable != null){
    		//?»è???
    		Bitmap b = Bitmap.createBitmap(drawable.getBitmap(),0,0,mTextWidth,mTextHeight);
    		canvas.drawBitmap(b, matrix, paint);
    	}
        //?»å?
        draw(canvas, this.text);
    } 
    
    private void draw(Canvas canvas, String thetext) {
    	char ch;
    	mTextPosy = 0;//?å??–y?æ?
    	mTextPosx = mTextWidth - mLineWidth;//?å??–x?æ?    	
        for (int i = 0; i < this.TextLength; i++) {
        	ch = thetext.charAt(i);
        	if (ch == '\n') {
        		mTextPosx -= mLineWidth;// ?¢å?
    	    	mTextPosy = 0;
        	} else {
        		mTextPosy += mFontHeight;
        		if (mTextPosy > this.mTextHeight) {
        			mTextPosx -= mLineWidth;// ?¢å?
        			i--;
        			mTextPosy = 0;
        		}else{
        			canvas.drawText(String.valueOf(ch), mTextPosx, mTextPosy, paint);
        		}
        	}	
        }
    }
    //è®¡ç??‡å?è¡Œæ•°?Œæ?å®?
    private void GetTextInfo() {
    	Log.v("TextViewVertical","GetTextInfo");
    	char ch;
    	int h = 0;
    	paint.setTextSize(mFontSize);
    	//?·å?å­—å®½
    	if(mLineWidth == 0){
    		float[] widths = new float[1];
    		paint.getTextWidths("¥¿", widths);//?·å??•ä¸ªæ±‰å??„å®½åº?
    		mLineWidth = (int) Math.ceil(widths[0] * 1.1 +2);
    	}
    	
    	FontMetrics fm = paint.getFontMetrics();  
      	mFontHeight = (int) (Math.ceil(fm.descent - fm.top) * 1.0);// ?·å?å­—ä?é«˜åº¦
      	
      	//è®¡ç??‡å?è¡Œæ•°
      	mRealLine=0;
    	for (int i = 0; i < this.TextLength; i++) {
    		ch = this.text.charAt(i);
    		if (ch == '\n') {
	    	    mRealLine++;// ?Ÿå??„è??°å?ä¸?
	    	    h = 0;
    		} else {
    			h += mFontHeight;
    			if (h > this.mTextHeight) {
    				mRealLine++;// ?Ÿå??„è??°å?ä¸?
    				i--;
    				h = 0;
    			} else {
    				if (i == this.TextLength - 1) {
    					mRealLine++;// ?Ÿå??„è??°å?ä¸?
    				}
    			}
    	   }
    	}
    	mRealLine++;//é¢å?å¢å?ä¸??
    	mTextWidth = mLineWidth*mRealLine;//è®¡ç??‡å??»å®½åº?
    	measure(mTextWidth, getHeight());//?æ–°è°ƒæ•´å¤§å?
        layout(getLeft(), getTop(), getLeft()+mTextWidth, getBottom());//?æ–°ç»˜åˆ¶å®¹å™¨
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
		mTextHeight=result;//è®¾ç½®?‡æœ¬é«˜åº¦
		return result;  
	}  
}
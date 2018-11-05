/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ece.course.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import com.google.android.gms.vision.face.Face;

class FaceGraphic extends GraphicOverlay.Graphic {

  private static final String TAG = "FaceGraphic";

  private static final float DOT_RADIUS = 3.0f;
  private static final float TEXT_OFFSET_Y = -30.0f;

  private boolean mIsFrontFacing;

  // This variable may be written to by one of many threads. By declaring it as volatile,
  // we guarantee that when we read its contents, we're reading the most recent "write"
  // by any thread.
  private volatile FaceData mFaceData;

  private Paint mHintTextPaint;
  private Paint mHintOutlinePaint;
  private Paint mEyeWhitePaint;
  private Paint mIrisPaint;
  private Paint mEyeOutlinePaint;
  private Paint mEyelidPaint;

  private Drawable mPigNoseGraphic;
  private Drawable mMustacheGraphic;
  private Drawable mHappyStarGraphic;
  private Drawable mHatGraphic;


  FaceGraphic(GraphicOverlay overlay, Context context, boolean isFrontFacing) {
    super(overlay);
    mIsFrontFacing = isFrontFacing;
    Resources resources = context.getResources();
    initializePaints(resources);
    initializeGraphics(resources);
  }

  private void initializeGraphics(Resources resources) {
    mPigNoseGraphic = resources.getDrawable(R.drawable.switch_cameras);
    mMustacheGraphic = resources.getDrawable(R.drawable.switch_cameras);
    mHappyStarGraphic = resources.getDrawable(R.drawable.switch_cameras);
    mHatGraphic = resources.getDrawable(R.drawable.switch_cameras);
  }

  private void initializePaints(Resources resources) {
    mHintTextPaint = new Paint();
    mHintTextPaint.setColor(resources.getColor(R.color.overlayHint));
    mHintTextPaint.setTextSize(resources.getDimension(R.dimen.textSize));

    mHintOutlinePaint = new Paint();
    mHintOutlinePaint.setColor(resources.getColor(R.color.overlayHint));
    mHintOutlinePaint.setStyle(Paint.Style.STROKE);
    mHintOutlinePaint.setStrokeWidth(resources.getDimension(R.dimen.hintStroke));

    mEyeWhitePaint = new Paint();
    mEyeWhitePaint.setColor(resources.getColor(R.color.eyeWhite));
    mEyeWhitePaint.setStyle(Paint.Style.FILL);

    mIrisPaint = new Paint();
    mIrisPaint.setColor(resources.getColor(R.color.iris));
    mIrisPaint.setStyle(Paint.Style.FILL);

    mEyeOutlinePaint = new Paint();
    mEyeOutlinePaint.setColor(resources.getColor(R.color.eyeOutline));
    mEyeOutlinePaint.setStyle(Paint.Style.STROKE);
    mEyeOutlinePaint.setStrokeWidth(resources.getDimension(R.dimen.eyeOutlineStroke));

    mEyelidPaint = new Paint();
    mEyelidPaint.setColor(resources.getColor(R.color.eyelid));
    mEyelidPaint.setStyle(Paint.Style.FILL);
  }

  void update(FaceData faceData) {
    mFaceData = faceData;
    postInvalidate(); // Trigger a redraw of the graphic (i.e. cause draw() to be called).
  }

  @Override
  public void draw(Canvas canvas) {
//    Face face=mface;
    final float DOT_RADIUS = 3.0f;
    final float TEXT_OFFSET_Y = -30.0f;
// Confirm that the face and its features are still visible before drawing any graphics over it.
    if (mFaceData == null) {
      return;
    }
// 1
    PointF detectPosition = mFaceData.getPosition();
    PointF detectLeftEyePosition = mFaceData.getLeftEyePosition();
    PointF detectRightEyePosition = mFaceData.getRightEyePosition();
    PointF detectNoseBasePosition = mFaceData.getNoseBasePosition();
    PointF detectMouthLeftPosition = mFaceData.getMouthLeftPosition();
    PointF detectMouthBottomPosition = mFaceData.getMouthBottomPosition();
    PointF detectMouthRightPosition = mFaceData.getMouthRightPosition();
    if ((detectPosition == null) ||
            (detectLeftEyePosition == null) ||
            (detectRightEyePosition == null) ||
            (detectNoseBasePosition == null) ||
            (detectMouthLeftPosition == null) ||
            (detectMouthBottomPosition == null) ||
            (detectMouthRightPosition == null)) {
      return;
    }
// 2
    float leftEyeX = translateX(detectLeftEyePosition.x);
    float leftEyeY = translateY(detectLeftEyePosition.y);
    canvas.drawCircle(leftEyeX, leftEyeY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("left eye", leftEyeX, leftEyeY + TEXT_OFFSET_Y, mHintTextPaint);
    float rightEyeX = translateX(detectRightEyePosition.x);
    float rightEyeY = translateY(detectRightEyePosition.y);
    canvas.drawCircle(rightEyeX, rightEyeY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("right eye", rightEyeX, rightEyeY + TEXT_OFFSET_Y, mHintTextPaint);
    float noseBaseX = translateX(detectNoseBasePosition.x);
    float noseBaseY = translateY(detectNoseBasePosition.y);
    canvas.drawCircle(noseBaseX, noseBaseY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("nose base", noseBaseX, noseBaseY + TEXT_OFFSET_Y, mHintTextPaint);
    float mouthLeftX = translateX(detectMouthLeftPosition.x);
    float mouthLeftY = translateY(detectMouthLeftPosition.y);
    canvas.drawCircle(mouthLeftX, mouthLeftY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("mouth left", mouthLeftX, mouthLeftY + TEXT_OFFSET_Y, mHintTextPaint);
    float mouthRightX = translateX(detectMouthRightPosition.x);
    float mouthRightY = translateY(detectMouthRightPosition.y);
    canvas.drawCircle(mouthRightX, mouthRightY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("mouth right", mouthRightX, mouthRightY + TEXT_OFFSET_Y,
            mHintTextPaint);
    float mouthBottomX = translateX(detectMouthBottomPosition.x);
    float mouthBottomY = translateY(detectMouthBottomPosition.y);
    canvas.drawCircle(mouthBottomX, mouthBottomY, DOT_RADIUS, mHintOutlinePaint);
    canvas.drawText("mouth bottom", mouthBottomX, mouthBottomY + TEXT_OFFSET_Y,
            mHintTextPaint);


    // 3
    float centerX = translateX(mFaceData.getPosition().x + mFaceData.getWidth() / 2.0f);
    float centerY = translateY(mFaceData.getPosition().y + mFaceData.getHeight() / 2.0f);
    float offsetX = scaleX(mFaceData.getWidth() / 2.0f);
    float offsetY = scaleY(mFaceData.getHeight() / 2.0f);

    // 4
    // Draw a box around the face.
    float left = centerX - offsetX;
    float right = centerX + offsetX;
    float top = centerY - offsetY;
    float bottom = centerY + offsetY;

    // 5
    canvas.drawRect(left, top, right, bottom, mHintOutlinePaint);

    // 6
    // Draw the face's id.
    canvas.drawText(String.format("id: %d", mFaceData.getId()), centerX, centerY, mHintTextPaint);

  }

}

/*
 Copyright (c) 2012 Roman Truba

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.dreamtobe.touchgallery.TouchView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends BufferedInputStream
{
	protected long mContentLen, mBytesLoaded;
	protected InputStreamProgressListener mProgressListener;
	public InputStreamWrapper(InputStream in, int size, long contentLen)
	{
		super(in, size);
		mContentLen = contentLen;
		mBytesLoaded = 0;
	}

	@Override
	public synchronized int read(byte[] buffer, int offset, int byteCount)
			throws IOException
	{
		mBytesLoaded += byteCount;
		if (mProgressListener != null) 
		{
			mProgressListener.onProgress(mBytesLoaded * 1.0f / mContentLen, mBytesLoaded, mContentLen);
		}
		return super.read(buffer, offset, byteCount);
	}
	
	public void setProgressListener(InputStreamProgressListener listener)
	{
		mProgressListener = listener;
	}
	
	public static interface InputStreamProgressListener 
	{
		public void onProgress(float progressValue, long bytesLoaded, long bytesTotal);		
	}

	
}

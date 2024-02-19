package com.example.matchtiles;

public class CardInfo {
    private int imageViewId;
    private int drawableId;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    public CardInfo(int imageViewId, int drawableId) {
        this.imageViewId = imageViewId;
        this.drawableId = drawableId;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "imageViewId=" + imageViewId +
                ", drawableId=" + drawableId +
                ", isFlipped=" + isFlipped +
                ", isMatched=" + isMatched +
                '}';
    }
}

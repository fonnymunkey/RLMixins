package rlmixins.wrapper;

import goblinbob.mobends.standard.previewer.PlayerPreviewer;

public abstract class MobendsWrapper {
    public static void clearPlayerPreview() {
        PlayerPreviewer.deletePreviewData();
    }
}
package backend.academy.bot.app;

public enum ChatState {
    NONE,
    READY,
    WAITING_UNTRACKED_LINK,
    WAITING_TRACKED_LINK,
    WAITING_TEG,
    WAITING_FILTER
}

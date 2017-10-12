package org.brainail.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class L {

    public enum LogPriority {
        /**
         * Priority constant for the println method; use Log.v.
         */
        VERBOSE,
        /**
         * Priority constant for the println method; use Log.d.
         */
        DEBUG,
        /**
         * Priority constant for the println method; use Log.i.
         */
        INFO,
        /**
         * Priority constant for the println method; use Log.w.
         */
        WARN,
        /**
         * Priority constant for the println method; use Log.e.
         */
        ERROR,
        /**
         * Priority constant for the println method.
         */
        ASSERT
    }

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    public static void v(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    /**
     * Log a verbose exception.
     */
    public static void v(Throwable t) {
        TREE_OF_SOULS.v(t);
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    public static void d(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    /**
     * Log a debug exception.
     */
    public static void d(Throwable t) {
        TREE_OF_SOULS.d(t);
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    public static void i(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    /**
     * Log an info exception.
     */
    public static void i(Throwable t) {
        TREE_OF_SOULS.i(t);
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.w(t, message, args);
    }

    /**
     * Log a warning exception.
     */
    public static void w(Throwable t) {
        TREE_OF_SOULS.w(t);
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    /**
     * Log an error exception.
     */
    public static void e(Throwable t) {
        TREE_OF_SOULS.e(t);
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    public static void wtf(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.wtf(t, message, args);
    }

    /**
     * Log an assert exception.
     */
    public static void wtf(Throwable t) {
        TREE_OF_SOULS.wtf(t);
    }

    /**
     * Log at {@code priority} a message with optional format args.
     */
    public static void log(LogPriority priority, String message, Object... args) {
        TREE_OF_SOULS.log(priority, message, args);
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    public static void log(LogPriority priority, Throwable t, String message, Object... args) {
        TREE_OF_SOULS.log(priority, t, message, args);
    }

    /**
     * Log at {@code priority} an exception.
     */
    public static void log(LogPriority priority, Throwable t) {
        TREE_OF_SOULS.log(priority, t);
    }

    /**
     * A view into Logger's planted trees as a tree itself. This can be used for injecting a logger
     * instance rather than using static methods or to facilitate testing.
     */
    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    /**
     * Set a one-time tag for use on the next logging call.
     */
    public static Tree tag(String tag) {
        Tree[] forest = forestAsArray;
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0, count = forest.length; i < count; i++) {
            forest[i].explicitTag.set(tag);
        }
        return TREE_OF_SOULS;
    }

    /**
     * Add a new logging tree.
     */
    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Logger into itself.");
        }
        synchronized (FOREST) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /**
     * Adds new logging trees.
     */
    public static void plant(Tree... trees) {
        if (trees == null) {
            throw new NullPointerException("trees == null");
        }
        for (Tree tree : trees) {
            if (tree == null) {
                throw new NullPointerException("trees contains null");
            }
            if (tree == TREE_OF_SOULS) {
                throw new IllegalArgumentException("Cannot plant Logger into itself.");
            }
        }
        synchronized (FOREST) {
            Collections.addAll(FOREST, trees);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /**
     * Remove a planted tree.
     */
    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (!FOREST.remove(tree)) {
                throw new IllegalArgumentException("Cannot uproot tree which is not planted: " + tree);
            }
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
        }
    }

    /**
     * Remove all planted trees.
     */
    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    /**
     * Return a copy of all planted {@linkplain Tree trees}.
     */
    public static List<Tree> forest() {
        synchronized (FOREST) {
            return unmodifiableList(new ArrayList<>(FOREST));
        }
    }

    public static int treeCount() {
        synchronized (FOREST) {
            return FOREST.size();
        }
    }

    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    // Both fields guarded by 'FOREST'.
    private static final List<Tree> FOREST = new ArrayList<>();
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    /**
     * A {@link Tree} that delegates to all planted trees in the {@linkplain #FOREST forest}.
     */
    private static final Tree TREE_OF_SOULS = new Tree() {
        @Override
        public void v(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(message, args);
            }
        }

        @Override
        public void v(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(t, message, args);
            }
        }

        @Override
        public void v(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].v(t);
            }
        }

        @Override
        public void d(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(message, args);
            }
        }

        @Override
        public void d(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(t, message, args);
            }
        }

        @Override
        public void d(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].d(t);
            }
        }

        @Override
        public void i(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(message, args);
            }
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(t, message, args);
            }
        }

        @Override
        public void i(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].i(t);
            }
        }

        @Override
        public void w(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(message, args);
            }
        }

        @Override
        public void w(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(t, message, args);
            }
        }

        @Override
        public void w(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].w(t);
            }
        }

        @Override
        public void e(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(message, args);
            }
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(t, message, args);
            }
        }

        @Override
        public void e(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].e(t);
            }
        }

        @Override
        public void wtf(String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(message, args);
            }
        }

        @Override
        public void wtf(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(t, message, args);
            }
        }

        @Override
        public void wtf(Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].wtf(t);
            }
        }

        @Override
        public void log(LogPriority priority, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, message, args);
            }
        }

        @Override
        public void log(LogPriority priority, Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t, message, args);
            }
        }

        @Override
        public void log(LogPriority priority, Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t);
            }
        }

        @Override
        protected void log(LogPriority priority, String tag, String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }
    };

    private L() {
        throw new AssertionError("No instances.");
    }

    /**
     * A facade for handling logging calls. Install instances via {@link #plant L.plant()}.
     */
    public static abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal<>();

        protected String getTag() {
            String tag = explicitTag.get();
            if (tag != null) {
                explicitTag.remove();
            }
            return tag;
        }

        /**
         * Log a verbose message with optional format args.
         */
        public void v(String message, Object... args) {
            prepareLog(LogPriority.VERBOSE, null, message, args);
        }

        /**
         * Log a verbose exception and a message with optional format args.
         */
        public void v(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.VERBOSE, t, message, args);
        }

        /**
         * Log a verbose exception.
         */
        public void v(Throwable t) {
            prepareLog(LogPriority.VERBOSE, t, null);
        }

        /**
         * Log a debug message with optional format args.
         */
        public void d(String message, Object... args) {
            prepareLog(LogPriority.DEBUG, null, message, args);
        }

        /**
         * Log a debug exception and a message with optional format args.
         */
        public void d(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.DEBUG, t, message, args);
        }

        /**
         * Log a debug exception.
         */
        public void d(Throwable t) {
            prepareLog(LogPriority.DEBUG, t, null);
        }

        /**
         * Log an info message with optional format args.
         */
        public void i(String message, Object... args) {
            prepareLog(LogPriority.INFO, null, message, args);
        }

        /**
         * Log an info exception and a message with optional format args.
         */
        public void i(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.INFO, t, message, args);
        }

        /**
         * Log an info exception.
         */
        public void i(Throwable t) {
            prepareLog(LogPriority.INFO, t, null);
        }

        /**
         * Log a warning message with optional format args.
         */
        public void w(String message, Object... args) {
            prepareLog(LogPriority.WARN, null, message, args);
        }

        /**
         * Log a warning exception and a message with optional format args.
         */
        public void w(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.WARN, t, message, args);
        }

        /**
         * Log a warning exception.
         */
        public void w(Throwable t) {
            prepareLog(LogPriority.WARN, t, null);
        }

        /**
         * Log an error message with optional format args.
         */
        public void e(String message, Object... args) {
            prepareLog(LogPriority.ERROR, null, message, args);
        }

        /**
         * Log an error exception and a message with optional format args.
         */
        public void e(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.ERROR, t, message, args);
        }

        /**
         * Log an error exception.
         */
        public void e(Throwable t) {
            prepareLog(LogPriority.ERROR, t, null);
        }

        /**
         * Log an assert message with optional format args.
         */
        public void wtf(String message, Object... args) {
            prepareLog(LogPriority.ASSERT, null, message, args);
        }

        /**
         * Log an assert exception and a message with optional format args.
         */
        public void wtf(Throwable t, String message, Object... args) {
            prepareLog(LogPriority.ASSERT, t, message, args);
        }

        /**
         * Log an assert exception.
         */
        public void wtf(Throwable t) {
            prepareLog(LogPriority.ASSERT, t, null);
        }

        /**
         * Log at {@code priority} a message with optional format args.
         */
        public void log(LogPriority priority, String message, Object... args) {
            prepareLog(priority, null, message, args);
        }

        /**
         * Log at {@code priority} an exception and a message with optional format args.
         */
        public void log(LogPriority priority, Throwable t, String message, Object... args) {
            prepareLog(priority, t, message, args);
        }

        /**
         * Log at {@code priority} an exception.
         */
        public void log(LogPriority priority, Throwable t) {
            prepareLog(priority, t, null);
        }

        /**
         * Return whether a message at {@code priority} should be logged.
         */
        @Deprecated
        protected boolean isLoggable(LogPriority priority) {
            return true;
        }

        /**
         * Return whether a message at {@code priority} or {@code tag} should be logged.
         */
        protected boolean isLoggable(String tag, LogPriority priority) {
            return isLoggable(priority);
        }

        private void prepareLog(LogPriority priority, Throwable t, String message, Object... args) {
            // Consume tag even when message is not loggable so that next message is correctly tagged.
            String tag = getTag();

            if (!isLoggable(tag, priority)) {
                return;
            }
            if (message != null && message.length() == 0) {
                message = null;
            }
            if (message == null) {
                if (t == null) {
                    return; // Swallow message if it's null and there's no throwable.
                }
                message = getStackTraceString(t);
            } else {
                if (args.length > 0) {
                    message = formatMessage(message, args);
                }
                if (t != null) {
                    message += "\n" + getStackTraceString(t);
                }
            }

            log(priority, tag, message, t);
        }

        /**
         * Formats a log message with optional arguments.
         */
        protected String formatMessage(String message, Object[] args) {
            return String.format(message, args);
        }

        private String getStackTraceString(Throwable t) {
            // Don't replace this with Log.getStackTraceString() - it hides
            // UnknownHostException, which is not what we want.
            StringWriter sw = new StringWriter(256);
            PrintWriter pw = new PrintWriter(sw, false);
            t.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }

        /**
         * Write a log message to its destination. Called for all level-specific methods by default.
         *
         * @param priority Log level. See {@link LogPriority} for constants.
         * @param tag      Explicit or inferred tag. May be {@code null}.
         * @param message  Formatted log message. May be {@code null}, but then {@code t} will not be.
         * @param t        Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
         */
        protected abstract void log(LogPriority priority, String tag, String message, Throwable t);
    }

}

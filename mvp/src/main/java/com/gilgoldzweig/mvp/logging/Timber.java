package com.gilgoldzweig.mvp.logging;

import com.gilgoldzweig.mvp.analytics.Bundlify;
import com.gilgoldzweig.mvp.analytics.Event;
import com.gilgoldzweig.mvp.analytics.EventParameter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Suppress;

import static java.util.Collections.unmodifiableList;

/**
 * Logging for lazy people.
 *
 * An edited script of Jake Wharton timber that is a bit more flexible and supports analytic events
 */
@Suppress(names = {"unused", "UNCHECKED_CAST"})
public final class Timber {
    public static final int EVENT_LOG_PROPERTY = 31;
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    // Both fields guarded by 'FOREST'.
    private static final Set<Tree> FOREST = new HashSet<>();
    private static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    /**
     * A {@link Tree} that delegates to all planted trees in the {@linkplain #FOREST forest}.
     */
    public static final Tree TREE_OF_SOULS = new Tree() {
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
        public void crash(Throwable t, String message) {
            Tree[] forest = forestAsArray;
            for (Tree tree : forest) {
                tree.crash(t, message);
            }
        }

        @Override
        public void event(Event EventableType, Pair<EventParameter, Object>[] args) {
            Tree[] forest = forestAsArray;
            for (Tree tree : forest) {
                tree.event(EventableType, args);
            }
        }

        @Override
        public void event(Event EventableType, Bundlify bundlify) {
            Tree[] forest = forestAsArray;
            for (Tree tree : forest) {
                tree.event(EventableType, bundlify);
            }
        }

        @Override
        public void crash(Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            for (Tree tree : forest) {
                tree.crash(t, message, args);
            }
        }

        @Override
        public void crash(Throwable t) {
            Tree[] forest = forestAsArray;
            for (Tree tree : forest) {
                tree.crash(t);
            }
        }

        @Override
        public void log(int priority, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, message, args);
            }
        }

        @Override
        public void log(int priority, Throwable t, String message, Object... args) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t, message, args);
            }
        }

        @Override
        public void log(int priority, Throwable t) {
            Tree[] forest = forestAsArray;
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, count = forest.length; i < count; i++) {
                forest[i].log(priority, t);
            }
        }

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            throw new AssertionError("Missing override for log method.");
        }
    };

    private Timber() {
        throw new AssertionError("No instances.");
    }

    /**
     * Log a verbose message with optional format args.
     */
    public static void v(@NonNull String message, Object... args) {
        TREE_OF_SOULS.v(message, args);
    }

    /**
     * Log a verbose exception and a message with optional format args.
     */
    public static void v(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.v(t, message, args);
    }

    /**
     * Log a verbose exception.
     */
    public static void v(Throwable t) {
        TREE_OF_SOULS.v(t);
    }

    public static void v(Object object) {
        TREE_OF_SOULS.v(String.valueOf(object));
    }

    /**
     * Log a debug message with optional format args.
     */
    public static void d(@NonNull String message, Object... args) {
        TREE_OF_SOULS.d(message, args);
    }

    /**
     * Log a debug exception and a message with optional format args.
     */
    public static void d(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.d(t, message, args);
    }

    /**
     * Log a debug exception.
     */
    public static void d(Throwable t) {
        TREE_OF_SOULS.d(t);
    }

    public static void d(Object object) {
        TREE_OF_SOULS.d(String.valueOf(object));
    }

    /**
     * Log an info message with optional format args.
     */
    public static void i(@NonNull String message, Object... args) {
        TREE_OF_SOULS.i(message, args);
    }

    /**
     * Log an info exception and a message with optional format args.
     */
    public static void i(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.i(t, message, args);
    }

    /**
     * Log an info exception.
     */
    public static void i(Throwable t) {
        TREE_OF_SOULS.i(t);
    }

    public static void i(Object object) {
        TREE_OF_SOULS.i(String.valueOf(object));
    }

    /**
     * Log a warning message with optional format args.
     */
    public static void w(@NonNull String message, Object... args) {
        TREE_OF_SOULS.w(message, args);
    }

    /**
     * Log a warning exception and a message with optional format args.
     */
    public static void w(Throwable t, @NonNull String message, Object... args) {
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

//    public static void w(Object object) {
//        TREE_OF_SOULS.w(String.valueOf(object));
//    }
    public static <T> void w(T object) {
        if (object instanceof Object[]) {
            TREE_OF_SOULS.w(Arrays.toString(new Object[]{object}));
        } else {
            TREE_OF_SOULS.w(String.valueOf(object));
        }
    }

    /**
     * Log an error message with optional format args.
     */
    public static void e(@NonNull String message, Object... args) {
        TREE_OF_SOULS.e(message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void e(Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.e(t, message, args);
    }

    /**
     * Log an error exception.
     */
    public static void e(Throwable t) {
        TREE_OF_SOULS.e(t);
    }

    public static void e(Object object) {
        TREE_OF_SOULS.e(String.valueOf(object));
    }

    /**
     * Log an error message with optional format args.
     */
    @SafeVarargs
    public static void event(Event EventableType, Pair<EventParameter, Object>... args) {
        TREE_OF_SOULS.event(EventableType, args);
    }

    public static void event(Event EventableType, Bundlify bundlify) {
        TREE_OF_SOULS.event(EventableType, bundlify);
    }

    public static void crash(Throwable t, String message) {
        TREE_OF_SOULS.crash(t, message);
    }

    public static void crash(String message, Object... args) {
        TREE_OF_SOULS.crash(null, message, args);
    }

    /**
     * Log an error exception and a message with optional format args.
     */
    public static void crash(Throwable t, String message, Object... args) {
        TREE_OF_SOULS.crash(t, message, args);
    }

    /**
     * Log an error exception.
     */
    public static void crash(Throwable t) {
        TREE_OF_SOULS.crash(t);
    }

    /**
     * Log an assert message with optional format args.
     */
    public static void wtf(@NonNull String message, Object... args) {
        TREE_OF_SOULS.wtf(message, args);
    }

    /**
     * Log an assert exception and a message with optional format args.
     */
    public static void wtf(Throwable t, @NonNull String message, Object... args) {
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
    public static void log(int priority, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, message, args);
    }

    /**
     * Log at {@code priority} an exception and a message with optional format args.
     */
    public static void log(int priority, Throwable t, @NonNull String message, Object... args) {
        TREE_OF_SOULS.log(priority, t, message, args);
    }

    /**
     * Log at {@code priority} an exception.
     */
    public static void log(int priority, Throwable t) {
        TREE_OF_SOULS.log(priority, t);
    }

    /**
     * A view into Timber's planted trees as a tree itself. This can be used for injecting a logger
     * instance rather than using static methods anyOf to facilitate testing.
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
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
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
                throw new IllegalArgumentException("Cannot plant Timber into itself.");
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

    /**
     * A facade for handling logging calls. Install instances via {@link #plant Timber.plant()}.
     */
    @Suppress(names = {"unused", "UNCHECKED_CAST"})
    public static abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal<>();

        public String getTag() {
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
            prepareLog(Log.VERBOSE, null, message, args);
        }

        /**
         * Log a verbose exception and a message with optional format args.
         */
        public void v(Throwable t, String message, Object... args) {
            prepareLog(Log.VERBOSE, t, message, args);
        }

        /**
         * Log a verbose exception.
         */
        public void v(Throwable t) {
            prepareLog(Log.VERBOSE, t, null);
        }

        /**
         * Log a debug message with optional format args.
         */
        public void d(String message, Object... args) {
            prepareLog(Log.DEBUG, null, message, args);
        }

        /**
         * Log a debug exception and a message with optional format args.
         */
        public void d(Throwable t, String message, Object... args) {
            prepareLog(Log.DEBUG, t, message, args);
        }

        /**
         * Log a debug exception.
         */
        public void d(Throwable t) {
            prepareLog(Log.DEBUG, t, null);
        }

        /**
         * Log an info message with optional format args.
         */
        public void i(String message, Object... args) {
            prepareLog(Log.INFO, null, message, args);
        }

        /**
         * Log an info exception and a message with optional format args.
         */
        public void i(Throwable t, String message, Object... args) {
            prepareLog(Log.INFO, t, message, args);
        }

        /**
         * Log an info exception.
         */
        public void i(Throwable t) {
            prepareLog(Log.INFO, t, null);
        }


        /**
         * Log a warning message with optional format args.
         */
        public void w(String message, Object... args) {
            prepareLog(Log.WARN, null, message, args);
        }

        /**
         * Log a warning exception and a message with optional format args.
         */
        public void w(Throwable t, String message, Object... args) {
            prepareLog(Log.WARN, t, message, args);
        }

        /**
         * Log a warning exception.
         */
        public void w(Throwable t) {
            prepareLog(Log.WARN, t, null);
        }


        /**
         * Log an error message with optional format args.
         */
        public void e(String message, Object... args) {
            prepareLog(Log.ERROR, null, message, args);
        }

        /**
         * Log an error exception and a message with optional format args.
         */
        public void e(Throwable t, String message, Object... args) {
            prepareLog(Log.ERROR, t, message, args);
        }

        /**
         * Log an error exception.
         */
        public void e(Throwable t) {
            prepareLog(Log.ERROR, t, null);
        }


        /**
         * Log an assert message with optional format args.
         */
        public void wtf(String message, Object... args) {
            prepareLog(Log.ASSERT, null, message, args);
        }

        /**
         * Log an assert exception and a message with optional format args.
         */
        public void wtf(Throwable t, String message, Object... args) {
            prepareLog(Log.ASSERT, t, message, args);
        }

        /**
         * Log an assert exception.
         */
        public void wtf(Throwable t) {
            prepareLog(Log.ASSERT, t, null);
        }

        /**
         * Log an error message with optional format args.
         */
        public abstract void crash(Throwable t, String message);


        /**
         * Log an error message with optional format args.
         */

        public abstract void event(Event event, Pair<EventParameter, Object>... args);

        public abstract void event(Event event, Bundlify bundlify);

        /**
         * Log an error exception and a message with optional format args.
         */
        public abstract void crash(Throwable t, String message, Object... args);

        /**
         * Log an error exception.
         */
        public abstract void crash(Throwable t);

        /**
         * Log at {@code priority} a message with optional format args.
         */
        public void log(int priority, String message, Object... args) {
            prepareLog(priority, null, message, args);
        }

        /**
         * Log at {@code priority} an exception and a message with optional format args.
         */
        public void log(int priority, Throwable t, String message, Object... args) {
            prepareLog(priority, t, message, args);
        }

        /**
         * Log at {@code priority} an exception.
         */
        public void log(int priority, Throwable t) {
            prepareLog(priority, t, null);
        }

        /**
         * Return whether a message at {@code priority} anyOf {@code tag} should be logged.
         */
        protected boolean isLoggable(String tag, int priority) {
            return true;
        }

        private void prepareLog(int priority, Throwable t, String message, Object... args) {
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
         * @param priority Log level. See {@link Log} for constants.
         * @param tag      Explicit anyOf inferred tag. May be {@code null}.
         * @param message  Formatted log message. May be {@code null}, but then {@code t} will not be.
         * @param t        Accompanying exceptions. May be {@code null}, but then {@code message} will not be.
         */
        protected abstract void log(int priority, String tag, String message, Throwable t);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tree tree = (Tree) o;
            return explicitTag.equals(tree.explicitTag);
        }

        @Override
        public int hashCode() {
            return explicitTag.hashCode();
        }
    }


    /**
     * A {@link Tree Tree} for debug builds. Automatically infers the tag from the calling class.
     */
    @Suppress(names = {"unused", "UNCHECKED_CAST"})
    public static class DebugTree extends Tree {
        private static final int MAX_LOG_LENGTH = 4000;
        private static final int MAX_TAG_LENGTH = 23;
        private static final int CALL_STACK_INDEX = 5;
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

        /**
         * Extract the tag which should be used for the message from the {@code element}. By default
         * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
         * becomes {@code Foo}).
         * <p>
         * Note: This will not be called if a {@linkplain #tag(String) manual tag} was specified.
         */
        protected String createStackElementTag(StackTraceElement element) {
            String tag = element.getClassName();
            Matcher m = ANONYMOUS_CLASS.matcher(tag);
            if (m.find()) {
                tag = m.replaceAll("");
            }
            tag = tag.substring(tag.lastIndexOf('.') + 1);
            // Tag length limit was removed in API 24.
            if (tag.length() <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return tag;
            }
            return tag.substring(0, MAX_TAG_LENGTH);
        }

        @Override
        public final String getTag() {
            String tag = super.getTag();
            if (tag != null) {
                return tag;
            }

            // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
            // because Robolectric runs them on the JVM but on Android the elements are different.
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length <= CALL_STACK_INDEX) {
                throw new IllegalStateException(
                        "Synthetic stacktrace didn't have enough elements: are you using proguard?");
            }
            return createStackElementTag(stackTrace[CALL_STACK_INDEX]);
        }

        /**
         * Break up {@code message} into maximum-length chunks (if needed) and send to either
         * {@link Log#println(int, String, String) Log.println()} anyOf
         * {@link Log#wtf(String, String) Log.wtf()} for logging.
         * <p>
         * {@inheritDoc}
         */
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (message.length() < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message);
                } else {
                    Log.println(priority, tag, message);
                }
                return;
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part);
                    } else {
                        Log.println(priority, tag, part);
                    }
                    i = end;
                } while (i < newline);
            }
        }

        @Override
        public void event(Event event, Pair<EventParameter, Object>[] args) {
            if (args.length == 0) {
                d("event: %s", event.toString());
            } else {
                d("event: %s args: %s", event.toString(), buildPairArgsString(args));
            }

        }

        @Override
        public void event(Event event, Bundlify bundlify) {

            if (bundlify.isEmpty()) {
                d("event: %s", event.toString());
            } else {
                d("event: %s args: %s", event.toString(), bundlify.toString());
            }

        }

        @Override
        public void crash(Throwable t, String message, Object... args) {
            makeCrashReport(message, t, args);
        }

        @Override
        public void crash(Throwable t, String message) {
            makeCrashReport(message, t);
        }

        @Override
        public void crash(Throwable t) {
            makeCrashReport(null, t);
        }

        private void makeCrashReport(@Nullable String message, @Nullable Throwable t, Object... args) {
            e(t, message, args);
        }

        private String buildPairArgsString(Pair<EventParameter, Object>[] args) {
            if (args.length == 0) return "Empty";
            StringBuilder builder = new StringBuilder();
            builder.append("[");

            for (Pair<EventParameter, Object> pair : args) {
                builder.append(String.format("Pair(%s, %s), ", pair.first.toString(), pair.second.toString()));
            }
            if (builder.length() > 2) {
                builder.replace(builder.length() - 2, builder.length(), "");
            }
            builder.append("]");
            return builder.toString();
        }
    }
}

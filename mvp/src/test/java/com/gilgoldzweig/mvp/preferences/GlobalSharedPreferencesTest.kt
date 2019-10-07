package com.gilgoldzweig.mvp.preferences

import android.content.SharedPreferences
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Test for [GlobalSharedPreferences] using a mock version of SharedPreferences
 */
class GlobalSharedPreferencesTest {

    private var propertyDelegation: String by keyPreference(EXISTING_PROPERTY_DELEGATION_TEST_KEY, "")

    /**
     * Our mocked SharedPreference
     */
    private var sharedPreferences: MockSharedPreference = MockSharedPreference()

    /**
     * Our mocked SharedPreference.Editor
     */
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    /**
     * Initialize the [GlobalSharedPreferences] with our mocked SharedPreferences
     */
    @Before
    fun setUp() {
        GlobalSharedPreferences.initialize(sharedPreferences)
    }

    /**
     * Checking if [GlobalSharedPreferences.contains] return true on on a committed value
     */
    @Test
    fun testContains() {
        editor.putString(CONTAINS_TEST_KEY, CONTAINS_TEST_VALUE).commit()

        assertTrue(GlobalSharedPreferences.contains(CONTAINS_TEST_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [String] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutStringWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_STRING_KEY, EXISTING_STRING_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_STRING_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [Int] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutIntWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_INT_KEY, EXISTING_INT_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_INT_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [Long] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutLongWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_LONG_KEY, EXISTING_LONG_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_LONG_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [Float] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutFloatWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_FLOAT_KEY, EXISTING_FLOAT_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_FLOAT_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [Boolean] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutBooleanWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_BOOLEAN_KEY, EXISTING_BOOLEAN_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_BOOLEAN_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Put's a new [Set<String>] value to the editor but does not perform commit/apply on the editor
     */
    @Test
    fun testPutStringSetWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_STRING_SET_KEY, EXISTING_STRING_SET_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_STRING_SET_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Calls the right set method based on the "Generic"
     */
    @Test
    fun testPutAnySuccessWithoutCommit() {
        GlobalSharedPreferences.Editor.set(EXISTING_ANY_SUCCESS_KEY, EXISTING_ANY_SUCCESS_VALUE)

        assertTrue(sharedPreferences.uncommittedPreferenceMap.contains(EXISTING_ANY_SUCCESS_KEY))
    }

    /**
     * Checks if [GlobalSharedPreferences.set]
     * Throws UnsupportedOperationException
     */
    @Test
    fun testPutAnyFailureWithoutCommit() {
        try {
            GlobalSharedPreferences.Editor.set(EXISTING_ANY_FAILURE_KEY, EXISTING_ANY_FAILURE_VALUE)
        } catch (e: Exception) {
            assertTrue(e is UnsupportedOperationException)
        }


    }

    /**
     * Checks if [GlobalSharedPreferences.commit] performs the right actions based on previous actions
     */
    @Test
    fun testCommit() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.set(CONTAINS_TEST_KEY, CONTAINS_TEST_VALUE)
                .set(TEST_REMOVE_KEY, TEST_REMOVE_VALUE)
                .set(EXISTING_STRING_KEY, EXISTING_STRING_VALUE)
                .set(EXISTING_LONG_KEY, EXISTING_LONG_VALUE)
                .set(EXISTING_FLOAT_KEY, EXISTING_FLOAT_VALUE)
                .set(EXISTING_BOOLEAN_KEY, EXISTING_BOOLEAN_VALUE)
                .set(EXISTING_STRING_SET_KEY, EXISTING_STRING_SET_VALUE)
                .remove(TEST_REMOVE_KEY)
                .commit()

            assertTrue(contains(CONTAINS_TEST_KEY))
            assertFalse(contains(TEST_REMOVE_KEY))
            assertTrue(contains(EXISTING_STRING_KEY))
            assertTrue(contains(EXISTING_LONG_KEY))
            assertTrue(contains(EXISTING_FLOAT_KEY))
            assertTrue(contains(EXISTING_BOOLEAN_KEY))
            assertTrue(contains(EXISTING_STRING_SET_KEY))
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getString] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetString() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(EXISTING_STRING_KEY, EXISTING_STRING_VALUE)

            assertSame(getString(EXISTING_STRING_KEY), EXISTING_STRING_VALUE)

            assertSame(
                getString(NON_EXISTING_STRING_KEY, NON_EXISTING_STRING_DEFAULT_VALUE),
                NON_EXISTING_STRING_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getInt] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetInt() {

        GlobalSharedPreferences.Editor.commit(EXISTING_INT_KEY, EXISTING_INT_VALUE)

        with(GlobalSharedPreferences) {
            assertTrue(getInt(EXISTING_INT_KEY) == EXISTING_INT_VALUE)

            assertTrue(
                getInt(NON_EXISTING_INT_KEY, NON_EXISTING_INT_DEFAULT_VALUE) ==
                        NON_EXISTING_INT_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getLong] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetLong() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(EXISTING_LONG_KEY, EXISTING_LONG_VALUE)

            assertTrue(getLong(EXISTING_LONG_KEY) == EXISTING_LONG_VALUE)

            assertTrue(
                getLong(NON_EXISTING_LONG_KEY, NON_EXISTING_LONG_DEFAULT_VALUE) ==
                        NON_EXISTING_LONG_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getFloat] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetFloat() {
        GlobalSharedPreferences.Editor.commit(EXISTING_FLOAT_KEY, EXISTING_FLOAT_VALUE)
        with(GlobalSharedPreferences) {
            assertTrue(getFloat(EXISTING_FLOAT_KEY) == EXISTING_FLOAT_VALUE)

            assertTrue(
                getFloat(NON_EXISTING_FLOAT_KEY, NON_EXISTING_FLOAT_DEFAULT_VALUE) ==
                        NON_EXISTING_FLOAT_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getBoolean] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetBoolean() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(EXISTING_BOOLEAN_KEY, EXISTING_BOOLEAN_VALUE)

            assertSame(getBoolean(EXISTING_BOOLEAN_KEY), EXISTING_BOOLEAN_VALUE)

            assertSame(
                getBoolean(NON_EXISTING_BOOLEAN_KEY, NON_EXISTING_BOOLEAN_DEFAULT_VALUE),
                NON_EXISTING_BOOLEAN_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.getStringSet] returns the right value on an existing key
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetStringSet() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(
                EXISTING_STRING_SET_KEY,
                EXISTING_STRING_SET_VALUE
            )

            assertTrue(getStringSet(EXISTING_STRING_SET_KEY) == EXISTING_STRING_SET_VALUE)

            assertTrue(
                getStringSet(NON_EXISTING_STRING_SET_KEY, NON_EXISTING_STRING_SET_DEFAULT_VALUE) ==
                        NON_EXISTING_STRING_SET_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.get] calls the right get function based on the default value
     * and returns the default value when no preference was found
     */
    @Test
    fun testGetAnySuccess() {
        GlobalSharedPreferences.Editor.commit(EXISTING_ANY_SUCCESS_KEY, EXISTING_ANY_SUCCESS_VALUE)
        with(GlobalSharedPreferences) {
            assertTrue(
                get(EXISTING_ANY_SUCCESS_KEY, NON_EXISTING_ANY_DEFAULT_VALUE) ==
                        EXISTING_ANY_SUCCESS_VALUE
            )

            assertTrue(
                get(NON_EXISTING_ANY_KEY, NON_EXISTING_ANY_DEFAULT_VALUE) ==
                        NON_EXISTING_ANY_DEFAULT_VALUE
            )
        }
    }

    /**
     * Checks if [GlobalSharedPreferences.get]
     * throws [UnsupportedOperationException] when called with an unsupported value
     *
     */
    @Test
    fun testGetAnyFailure() {
        try {
            GlobalSharedPreferences.get(EXISTING_ANY_FAILURE_KEY, EXISTING_ANY_FAILURE_VALUE)
        } catch (e: Exception) {
            assertTrue(e is UnsupportedOperationException)
        }
    }

    /**
     * Checks that [GlobalSharedPreferences.removeAndCommit] removes the value from the preferences
     */
    @Test
    fun testRemove() {
        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(TEST_REMOVE_KEY, TEST_REMOVE_VALUE)

            GlobalSharedPreferences.Editor.remove(TEST_REMOVE_KEY).commit()

            assertFalse(contains(TEST_REMOVE_KEY))
        }
    }

    /**
     * Testing that [PreferencesProperty.setValue] saves value
     */
    @Test
    fun testPropertyDelegationSet() {
        propertyDelegation = PROPERTY_DELEGATION_TEST_VALUE

        with(GlobalSharedPreferences) {

            assertTrue(contains(EXISTING_PROPERTY_DELEGATION_TEST_KEY))

            assertTrue(
                get(
                    EXISTING_PROPERTY_DELEGATION_TEST_KEY,
                    NON_EXISTING_PROPERTY_DELEGATION_DEFAULT_VALUE
                ) == PROPERTY_DELEGATION_TEST_VALUE
            )
        }
    }

    /**
     * Testing that [PreferencesProperty.setValue] saves value
     */
    @Test
    fun testPropertyDelegationGet() {

        with(GlobalSharedPreferences) {
            GlobalSharedPreferences.Editor.commit(
                EXISTING_PROPERTY_DELEGATION_TEST_KEY,
                PROPERTY_DELEGATION_TEST_VALUE
            )

            assertTrue(propertyDelegation == PROPERTY_DELEGATION_TEST_VALUE)
        }
    }

    /**
     * We delete all the information held in the preferences
     */
    @After
    fun tearDown() {
        editor.clear()
    }
}

package com.gilgoldzweig.mvp.preferences

import android.content.SharedPreferences
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class GlobalSharedPreferencesTest {

  private var sharedPreferences: MockSharedPreference = MockSharedPreference()

  private var editor: SharedPreferences.Editor = sharedPreferences.edit()

  @Before
  fun setUp() {
    GlobalSharedPreferences.initialize(sharedPreferences)
  }

  @Test
  fun testContains() {
    editor.putString(CONTAINS_TEST_KEY, CONTAINS_TEST_VALUE).commit()

    assertTrue(GlobalSharedPreferences.contains(CONTAINS_TEST_KEY))
  }

  @Test
  fun testPutStringWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_STRING_KEY, EXISTING_STRING_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_STRING_KEY))
  }

  @Test
  fun testPutIntWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_INT_KEY, EXISTING_INT_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_INT_KEY))
  }

  @Test
  fun testPutLongWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_LONG_KEY, EXISTING_LONG_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_LONG_KEY))
  }

  @Test
  fun testPutFloatWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_FLOAT_KEY, EXISTING_FLOAT_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_FLOAT_KEY))
  }

  @Test
  fun testPutBooleanWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_BOOLEAN_KEY, EXISTING_BOOLEAN_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_BOOLEAN_KEY))
  }

  @Test
  fun testPutStringSetWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_STRING_SET_KEY, EXISTING_STRING_SET_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_STRING_SET_KEY))
  }

  @Test
  fun testPutAnySuccessWithoutCommit() {
    GlobalSharedPreferences.set(EXISTING_ANY_SUCCESS_KEY, EXISTING_ANY_SUCCESS_VALUE)

    assertTrue(sharedPreferences.uncommittedContains(EXISTING_ANY_SUCCESS_KEY))
  }

  @Test
  fun testPutAnyFailureWithoutCommit() {
    try {
      GlobalSharedPreferences.set(EXISTING_ANY_FAILURE_KEY, EXISTING_ANY_FAILURE_VALUE)
    } catch (e: Exception) {
      assertTrue(e is UnsupportedOperationException)
    }


  }

  @Test
  fun testCommit() {

    with(GlobalSharedPreferences) {

      set(CONTAINS_TEST_KEY, CONTAINS_TEST_VALUE)
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

  @Test
  fun testGetString() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_STRING_KEY, EXISTING_STRING_VALUE)

      assertSame(getString(EXISTING_STRING_KEY), EXISTING_STRING_VALUE)

      assertSame(getString(NON_EXISTING_STRING_KEY, NON_EXISTING_STRING_DEFAULT_VALUE),
                 NON_EXISTING_STRING_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetInt() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_INT_KEY, EXISTING_INT_VALUE)

      assertTrue(getInt(EXISTING_INT_KEY) == EXISTING_INT_VALUE)

      assertTrue(getInt(NON_EXISTING_INT_KEY, NON_EXISTING_INT_DEFAULT_VALUE) ==
                   NON_EXISTING_INT_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetLong() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_LONG_KEY, EXISTING_LONG_VALUE)

      assertTrue(getLong(EXISTING_LONG_KEY) == EXISTING_LONG_VALUE)

      assertTrue(getLong(NON_EXISTING_LONG_KEY, NON_EXISTING_LONG_DEFAULT_VALUE) ==
                   NON_EXISTING_LONG_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetFloat() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_FLOAT_KEY, EXISTING_FLOAT_VALUE)

      assertTrue(getFloat(EXISTING_FLOAT_KEY) == EXISTING_FLOAT_VALUE)

      assertTrue(getFloat(NON_EXISTING_FLOAT_KEY, NON_EXISTING_FLOAT_DEFAULT_VALUE) ==
                   NON_EXISTING_FLOAT_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetBoolean() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_BOOLEAN_KEY, EXISTING_BOOLEAN_VALUE)

      assertSame(getBoolean(EXISTING_BOOLEAN_KEY), EXISTING_BOOLEAN_VALUE)

      assertSame(getBoolean(NON_EXISTING_BOOLEAN_KEY, NON_EXISTING_BOOLEAN_DEFAULT_VALUE),
                 NON_EXISTING_BOOLEAN_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetStringSet() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_STRING_SET_KEY, EXISTING_STRING_SET_VALUE)

      assertTrue(getStringSet(EXISTING_STRING_SET_KEY) == EXISTING_STRING_SET_VALUE)

      assertTrue(getStringSet(NON_EXISTING_STRING_SET_KEY, NON_EXISTING_STRING_SET_DEFAULT_VALUE) ==
                   NON_EXISTING_STRING_SET_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetAnySuccess() {
    with(GlobalSharedPreferences) {
      commit(EXISTING_ANY_SUCCESS_KEY, EXISTING_ANY_SUCCESS_VALUE)

      assertTrue(get(EXISTING_ANY_SUCCESS_KEY, NON_EXISTING_ANY_DEFAULT_VALUE) == EXISTING_ANY_SUCCESS_VALUE)

      assertTrue(get(NON_EXISTING_ANY_KEY, NON_EXISTING_ANY_DEFAULT_VALUE) ==
                   NON_EXISTING_ANY_DEFAULT_VALUE
      )
    }
  }

  @Test
  fun testGetAnyFailure() {
    try {
      GlobalSharedPreferences.get(EXISTING_ANY_FAILURE_KEY, EXISTING_ANY_FAILURE_VALUE)
    } catch (e: Exception) {
      assertTrue(e is UnsupportedOperationException)
    }
  }

  @Test
  fun testRemove() {

    with(GlobalSharedPreferences) {
      commit(TEST_REMOVE_KEY, TEST_REMOVE_VALUE)

      removeAndCommit(TEST_REMOVE_KEY)

      assertFalse(contains(TEST_REMOVE_KEY))
    }
  }
}

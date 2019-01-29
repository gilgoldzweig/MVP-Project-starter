package com.gilgoldzweig.mvp.preferences

/**
 * Test keys, values, default values to test [GlobalSharedPreferences]
 * @see GlobalSharedPreferencesTest
 * @see MockSharedPreference
 */

const val EXISTING_PROPERTY_DELEGATION_TEST_KEY = "property_delegation_test_key"
const val PROPERTY_DELEGATION_TEST_VALUE = "property_delegation_test_value"
const val NON_EXISTING_PROPERTY_DELEGATION_TEST_KEY = "non_existing_property_delegation_test_key"
const val NON_EXISTING_PROPERTY_DELEGATION_DEFAULT_VALUE = "non_existing_property_delegation_default_value"

const val CONTAINS_TEST_KEY = "contains_test_key"
const val CONTAINS_TEST_VALUE = "some value"

const val EXISTING_STRING_KEY = "existing_string_key"
const val NON_EXISTING_STRING_KEY = "non_existing_string_key"
const val EXISTING_STRING_VALUE = "existing_string_value"
const val NON_EXISTING_STRING_DEFAULT_VALUE = "existing_string_default_value"

const val EXISTING_INT_KEY = "existing_int_key"
const val NON_EXISTING_INT_KEY = "non_existing_int_key"
const val EXISTING_INT_VALUE = 135
const val NON_EXISTING_INT_DEFAULT_VALUE = -1

const val EXISTING_LONG_KEY = "existing_long_key"
const val NON_EXISTING_LONG_KEY = "non_existing_long_key"
const val EXISTING_LONG_VALUE = 15L
const val NON_EXISTING_LONG_DEFAULT_VALUE = -1L

const val EXISTING_FLOAT_KEY = "existing_float_key"
const val NON_EXISTING_FLOAT_KEY = "non_existing_float_key"
const val EXISTING_FLOAT_VALUE = 135.5f
const val NON_EXISTING_FLOAT_DEFAULT_VALUE = 0.0f

const val EXISTING_BOOLEAN_KEY = "existing_boolean_key"
const val NON_EXISTING_BOOLEAN_KEY = "non_existing_boolean_key"
const val EXISTING_BOOLEAN_VALUE = true
const val NON_EXISTING_BOOLEAN_DEFAULT_VALUE = false

const val EXISTING_STRING_SET_KEY = "existing_string_set_key"
const val NON_EXISTING_STRING_SET_KEY = "non_existing_string_set_key"

val EXISTING_STRING_SET_VALUE = setOf("value1", "value2", "value3", "value4")
val NON_EXISTING_STRING_SET_DEFAULT_VALUE = emptySet<String>()


const val EXISTING_ANY_SUCCESS_KEY = "existing_any_success_key"
val EXISTING_ANY_SUCCESS_VALUE: Any = "test_any_success_value"
const val NON_EXISTING_ANY_KEY = "non_existing_any_key"
val NON_EXISTING_ANY_DEFAULT_VALUE: Any = "non_existing_default_value"
const val EXISTING_ANY_FAILURE_KEY = "existing_any_failure_key"
val EXISTING_ANY_FAILURE_VALUE = emptySet<Any>()


const val TEST_REMOVE_KEY = "test_remove_key"
const val TEST_REMOVE_VALUE = "test_remove_value"



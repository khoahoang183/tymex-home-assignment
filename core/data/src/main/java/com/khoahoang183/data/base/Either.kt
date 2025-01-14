package com.khoahoang183.data.base


/**
 * Thank @[Fernando Cejas](https://github.com/android10)}
 */
sealed class Either<out E, out S> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Error<out L>(val leftValue: L) : Either<L, Nothing>()
    data class Exception<out L>(val leftValue: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val rightValue: R) : Either<Nothing, R>()
    data object SuccessUpload: Either<Nothing, Nothing>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Success
     */
    val isSuccess get() = this is Success<S>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Error
     */
    val isError get() = this is Error<E>

    /**
     * Creates a Left type.
     * @see Error
     */
    fun <L> left(a: L) = Error(a)


    /**
     * Creates a Left type.
     * @see Success
     */
    fun <R> right(b: R) = Success(b)

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Error
     * @see Success
     */
    fun fold(fnL: (E) -> Any, fnR: (S) -> Any): Any =
        when (this) {
            is Error -> fnL(leftValue)
            is Exception -> fnL(leftValue)
            is Success -> fnR(rightValue)
            else -> {}
        }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Error -> Either.Error(leftValue)
        is Either.Exception -> Either.Exception(leftValue)
        is Either.Success -> fn(rightValue)
        else -> Either.SuccessUpload
    }

/**
 * Right-biased map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

/** Returns the value from this `Right` or the given argument if this is a `Left`.
 *  Right(12).getOrElse(17) RETURNS 12 and Left(12).getOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getOrElse(value: R): R =
    when (this) {
        is Either.Error -> value
        is Either.Exception -> value
        is Either.Success -> rightValue
        else -> value
    }

/**
 * Left-biased onFailure() FP convention dictates that when this class is Left, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Error) fn(leftValue) }

/**
 * Right-biased onSuccess() FP convention dictates that when this class is Right, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    this.apply { if (this is Either.Success) fn(rightValue) }
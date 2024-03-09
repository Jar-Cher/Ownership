package com.ownership.exceptions

class NoRuleFoundException(path: String) : OwnershipResolutionException("No rule found for file: $path")
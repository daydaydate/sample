package com.base.api.RestfulRequest;

public enum ResponseCacheStatus
{
    NewFromServer,
    NotModifiedFromServer,
    FreshFromCache,
    StaleFromCache,
}

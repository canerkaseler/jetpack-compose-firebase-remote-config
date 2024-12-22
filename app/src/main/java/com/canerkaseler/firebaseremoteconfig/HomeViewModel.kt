package com.canerkaseler.firebaseremoteconfig

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseRemoteConfigProvider: FirebaseRemoteConfigProvider,
): ViewModel() {
    val uiState = MutableStateFlow(value = HomeUiState())

    init {
        viewModelScope.launch {
            fetchRemoteConfig()
        }
    }

    private suspend fun fetchRemoteConfig() {
        firebaseRemoteConfigProvider
            .configKeys(
                keyList = listOf(
                    HomeScreenFeatureFlag.BREAKING_NEWS_MESSAGE.keyName,
                    HomeScreenFeatureFlag.IS_VISIBLE_BREAKING_NEWS_MESSAGE.keyName,
                    HomeScreenFeatureFlag.BREAKING_NEWS_COUNTS.keyName,
                )
            )
            .collectLatest { configMap ->
                uiState.update {
                    HomeUiState(
                        breakingNewsMessage = if (configMap.contains(HomeScreenFeatureFlag.BREAKING_NEWS_MESSAGE.keyName)) {
                            firebaseRemoteConfigProvider.getStringFlagValue(
                                map = configMap,
                                key = HomeScreenFeatureFlag.BREAKING_NEWS_MESSAGE.keyName,
                            )
                        } else {
                            uiState.value.breakingNewsMessage
                        },
                        isVisibleBreakingNewsMessage = if (configMap.contains(HomeScreenFeatureFlag.IS_VISIBLE_BREAKING_NEWS_MESSAGE.keyName)) {
                            firebaseRemoteConfigProvider.getBooleanFlagValue(
                                map = configMap,
                                key = HomeScreenFeatureFlag.IS_VISIBLE_BREAKING_NEWS_MESSAGE.keyName,
                            )
                        } else {
                            uiState.value.isVisibleBreakingNewsMessage
                        },
                        breakingNewsCount = if (configMap.contains(HomeScreenFeatureFlag.BREAKING_NEWS_COUNTS.keyName)) {
                            firebaseRemoteConfigProvider.getLongFlagValue(
                                map = configMap,
                                key = HomeScreenFeatureFlag.BREAKING_NEWS_COUNTS.keyName,
                            )
                        } else {
                            uiState.value.breakingNewsCount
                        }
                    )
                }
            }
    }
}

data class HomeUiState(
    val breakingNewsCount: Long =
        defaultValueMap[HomeScreenFeatureFlag.BREAKING_NEWS_COUNTS.keyName] as Long,

    val isVisibleBreakingNewsMessage: Boolean =
        defaultValueMap[HomeScreenFeatureFlag.IS_VISIBLE_BREAKING_NEWS_MESSAGE.keyName] as Boolean,

    val breakingNewsMessage: String =
        defaultValueMap[HomeScreenFeatureFlag.BREAKING_NEWS_MESSAGE.keyName] as String,
)


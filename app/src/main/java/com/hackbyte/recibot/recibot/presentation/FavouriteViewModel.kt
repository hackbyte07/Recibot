package com.hackbyte.recibot.recibot.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackbyte.recibot.firestore.domain.FirestoreRecipes
import com.hackbyte.recibot.firestore.domain.FirestoreRepository
import com.hackbyte.recibot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    var firestoreRecipes by mutableStateOf<List<FirestoreRecipes>>(emptyList())
        private set

    var message by mutableStateOf("")
        private set

    var isLoading by mutableStateOf("")
        private set

    var showPlaceHolderAnimation by mutableStateOf(true)
        private set

    fun getFavourites() {
        viewModelScope.launch {
            firestoreRepository.getFirestoreRecipes().collect {
                when (it) {
                    is Resource.Loading -> {
                        showPlaceHolderAnimation = false
                        firestoreRecipes = it.data ?: emptyList()
                    }
                    is Resource.Success -> {
                        showPlaceHolderAnimation = false
                        firestoreRecipes = it.data ?: emptyList()
                    }
                    is Resource.Error -> {
                        message = it.throwable?.message ?: "Unknown error"
                    }
                }
            }
        }
    }

}
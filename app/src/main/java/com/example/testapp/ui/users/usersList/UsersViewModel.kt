package com.example.testapp.ui.users.usersList

import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.SingleLiveEvent
import com.example.testapp.data.TestRepository
import com.example.testapp.data.network.response.UsersResponse
import javax.inject.Inject

class UsersViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {

    val usersLiveData: SingleLiveEvent<List<UsersResponse.User>> = SingleLiveEvent()
    var userSpecialty: UsersResponse.User.Specialty? = null

    fun getUsersForSpeciality(specialityId: Int) {
        launchCoroutineScope {
            testRepository.requestUsersList {
                it.fold(ifLeft = { error ->
                    //do nothing
                }, ifRight = {
                    val users =
                        it.data.response.filter { it.specialty.any { it.specialtyId == specialityId } }
                    usersLiveData.postValue(users)
                })
            }
        }
    }
}
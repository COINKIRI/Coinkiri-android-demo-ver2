package com.coinkiri.coinkiri.domain.user.usecase

import com.coinkiri.coinkiri.domain.user.entity.UserResponseEntity
import com.coinkiri.coinkiri.domain.user.repository.UserRepository
import jakarta.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<UserResponseEntity> =
        userRepository.getUser()
}

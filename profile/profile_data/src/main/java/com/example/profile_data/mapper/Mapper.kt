package com.example.profile_data.mapper

import com.example.profile_data.model.ProfileDataModel
import com.example.profile_domain.model.ProfileDomainModel

fun ProfileDataModel.toProfile() = ProfileDomainModel(
   id, image, completedLesson, completedTest, points, user, rank, totalLessons, totalQuestions

)
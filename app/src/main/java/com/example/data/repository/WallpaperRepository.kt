package com.example.data.repository

import com.example.core.util.Resource
import com.example.data.model.VideoClipInfo
import com.example.data.model.WallpaperCategory
import com.example.data.model.WallpaperItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface contract.
 * Decouples the UI layer from data storage mechanisms, facilitating easy scaling to
 * local Room databases, online REST/Firebase backends, or local MediaStore queries.
 */
interface WallpaperRepository {
    fun getWallpapersByCategory(category: WallpaperCategory): Flow<Resource<List<WallpaperItem>>>
    fun getMyVideos(): Flow<Resource<List<WallpaperItem>>>
    suspend fun getWallpaperById(id: String): Resource<WallpaperItem>
    suspend fun saveCustomVideoClip(info: VideoClipInfo): Resource<WallpaperItem>
}

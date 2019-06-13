package club.fdawei.mourouter.sample.base.service

import club.fdawei.mourouter.annotation.Provider
import club.fdawei.mourouter.api.inject.AutowiredProvider

/**
 * Created by david on 2019/06/06.
 */
@Provider(CustomSource::class, CustomSource2::class)
class CustomSourceProvider : AutowiredProvider() {
}
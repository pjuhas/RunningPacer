package sk.upjs.vma.runningpacer.feature_vdot.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RacePace
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimes
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData
import sk.upjs.vma.runningpacer.feature_vdot.domain.repository.DataRepository
import javax.inject.Inject


class DefaultDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<TableData>
) : DataRepository {

    override suspend fun setRaceDistances(distance: TableData) {
        dataStore.updateData {
            it.copy(
                racePace = RacePace(
                    distance.racePace.distanceMarathon,
                    distance.racePace.distanceHalfMarathon,
                    distance.racePace.distanceFiveteenk,
                    distance.racePace.distanceTenk,
                    distance.racePace.distanceFivek,
                    distance.racePace.distanceTwoMiles,
                    distance.racePace.distanceThreek,
                    distance.racePace.distanceOnemile,
                    distance.racePace.distanceOnefivem
                ),
                raceTimes = RaceTimes(
                    distance.raceTimes.distanceMarathon,
                    distance.raceTimes.distanceHalfMarathon,
                    distance.raceTimes.distanceFiveteenk,
                    distance.raceTimes.distanceTenk,
                    distance.raceTimes.distanceFivek,
                    distance.raceTimes.distanceTwoMiles,
                    distance.raceTimes.distanceThreek,
                    distance.raceTimes.distanceOnemile,
                    distance.raceTimes.distanceOnefivem
                ),
                vdot = distance.vdot
            )
        }
        Log.v("setRaceDistances", "Saved")
    }
}
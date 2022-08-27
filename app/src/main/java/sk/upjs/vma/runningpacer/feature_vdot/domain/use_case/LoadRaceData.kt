package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.content.Context
import android.content.res.AssetManager
import org.json.JSONObject
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimes
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.RaceTimesList

class LoadRaceData {
    operator fun invoke(context: Context): RaceTimesList {
        val listOfRaceTimes = RaceTimesList()

        val parsedRaceObject = JSONObject(context.assets.readAssetsFile("race_paces.json"))
        val parsedRaceArray = parsedRaceObject.getJSONArray("pace")


        for (i in 0 until parsedRaceArray.length()){
            listOfRaceTimes.raceList.add(
                RaceTimes(
                    vdot = parsedRaceArray.getJSONObject(i).getInt("VDOT"),
                    distanceMarathon = parsedRaceArray.getJSONObject(i).getString("M").fromStringToIntTime(),
                    distanceHalfMarathon = parsedRaceArray.getJSONObject(i).getString("HM").fromStringToIntTime(),
                    distanceFiveteenk = parsedRaceArray.getJSONObject(i).getString("15km").fromStringToIntTime(),
                    distanceTenk = parsedRaceArray.getJSONObject(i).getString("10km").fromStringToIntTime(),
                    distanceFivek = parsedRaceArray.getJSONObject(i).getString("5km").fromStringToIntTime(),
                    distanceTwoMiles = parsedRaceArray.getJSONObject(i).getString("3200m").fromStringToIntTime(),
                    distanceThreek = parsedRaceArray.getJSONObject(i).getString("3km").fromStringToIntTime(),
                    distanceOnemile = parsedRaceArray.getJSONObject(i).getString("1600m").fromStringToIntTime(),
                    distanceOnefivem = parsedRaceArray.getJSONObject(i).getString("1500m").fromStringToIntTime()
                )
            )
        }
        return listOfRaceTimes
    }

    private fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}

    private fun String.fromStringToIntTime(): Int {
        val timeArray = this.split(":")
        return RegularTimeToSec().invoke(timeArray[2], timeArray[1], timeArray[0])
    }
}

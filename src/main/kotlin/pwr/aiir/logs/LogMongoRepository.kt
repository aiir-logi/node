package pwr.aiir.logs


import com.mongodb.client.model.Filters
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.Function
import org.bson.types.ObjectId
import javax.inject.Singleton
import org.bson.conversions.Bson


@Singleton
class LogMongoRepository(private val mongoClient : MongoClient) {

    fun save(log: Log?): Single<Log?> {
        return Single.fromPublisher(
            getCollection().insertOne(log)
        ).map<Log?> { log }
    }

    fun findAll(): Single<List<Log?>?>? {
        return Flowable.fromPublisher(
            getCollection().find()
        ).toList()
    }

    fun findById(id: ObjectId): Maybe<Log?>? {
        return Flowable.fromPublisher(
            getCollection()
                .find(Filters.eq("_id", id))
                .limit(1)
        ).firstElement()
    }

    private fun getCollection(): MongoCollection<Log?> {
        return mongoClient
            .getDatabase("logs")
            .getCollection("log", Log::class.java)
    }
}

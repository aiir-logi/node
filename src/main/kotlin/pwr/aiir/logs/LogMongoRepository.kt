package pwr.aiir.logs


import ch.rasc.bsoncodec.math.BigIntegerStringCodec
import com.mongodb.client.model.Filters
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.functions.Function
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import javax.inject.Singleton
import org.bson.conversions.Bson
import com.mongodb.MongoClientSettings
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistries.fromCodecs
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider


@Singleton
class LogMongoRepository(private val mongoClient : MongoClient) {

    fun save(log: Log): Single<Log?> {
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

    fun findByFilter(filter : Bson): Single<List<Log?>?>? {
        return Flowable.fromPublisher(getCollection().find(filter)).toList()
    }

    private fun getCollection(): MongoCollection<Log?> {

        val codecRegistry: CodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
            fromCodecs(BigIntegerStringCodec())
        )
        return mongoClient
            .getDatabase("logs")
            .getCollection("log", Log::class.java)
    }
}

package com.bcsprostuti.tanim.bcsprostuti.managers

import PreferenceManager
import android.content.Context
import javax.inject.Singleton
import android.content.SharedPreferences
import kotlin.jvm.Synchronized
import android.text.TextUtils
import com.tanim.androidbasetemplate.crypto.EncryptorDescriptor
import com.tanim.androidbasetemplate.data.Constant

import java.lang.Exception
import java.util.*

//import androidx.room.TypeConverters;
/**
 * Created by tanim on 11/15/2017.
 */
@Singleton
class AppPreferenceManager(context: Context
) : PreferenceManager {
    private var fcmToken: String? = null
    private var timeDifference: Long? = null
    private val sharedPreferences: SharedPreferences

    //    public AppPreferenceManager() {
    //        sharedPreferences = App.getContext().getSharedPreferences("BCS Prostuti", Context.MODE_PRIVATE);
    //
    //    }
    //    public static AppPreferenceManager getInstance() {
    //        if (sharedPreferenceManager == null)
    //            sharedPreferenceManager = new AppPreferenceManager();
    //        return sharedPreferenceManager;
    //    }
    //    private SharedPreferences getSharedPreferences(String fileName){
    //        return App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    //    }
    @Synchronized
    override fun getApiToken(): String? {
        //SharedPreferences sharedPreferences = getInstance();//getSharedPreferences(Constant.LOGIN_SETTINGS);
        val apiToken = sharedPreferences.getString(Constant.API_TOKEN, null)

        return if (TextUtils.isEmpty(apiToken)) null else EncryptorDescriptor.decrypt(apiToken, true)
        //Timber.i("ApiToken %s",TypeConverterUtils.decrypt(apiToken,false));
    }

    override fun setApiToken(apiToken: String?) {
        var apiToken: String? = apiToken
        val editor = sharedPreferences.edit()
        if (apiToken == null) {
            editor.putString(Constant.API_TOKEN, null)
            editor.apply()
            return
        }
        if (!TextUtils.isEmpty(apiToken)) apiToken = TypeConverterUtils.encrypt(apiToken, true)
        editor.putString(Constant.API_TOKEN, apiToken)
        editor.apply()
    }

    override fun getFcmToken(): String? {
        if (fcmToken != null) {
            //Timber.d("Check token %s",fcmToken);
            return fcmToken
        }
        val apiToken = sharedPreferences.getString("fcm_token", null)
        if (TextUtils.isEmpty(apiToken)) return null
        //Timber.i("ApiToken %s",TypeConverterUtils.decrypt(apiToken,false));
        fcmToken = TypeConverterUtils.decrypt(apiToken, true)
        return fcmToken
    }

    override fun setFcmToken(token: String?) {
        var token: String? = token
        fcmToken = token
        val editor = sharedPreferences.edit()
        if (token == null) {
            editor.putString("fcm_token", null)
            editor.apply()
            return
        }
        if (!TextUtils.isEmpty(token)) token = TypeConverterUtils.encrypt(token, true)
        editor.putString("fcm_token", token)
        editor.apply()
    }

    override fun getDeviceId(): String {
        //SharedPreferences sharedPreferences = getInstance();//getSharedPreferences(Constant.LOGIN_SETTINGS);
        return try {
            val deviceId = sharedPreferences.getString(Constant.DEVICE_ID, null)
            if (TextUtils.isEmpty(deviceId)) {
                val id = UUID.randomUUID().toString()
                setDeviceId(id)
                return id
            }
            deviceId!! //TypeConverterUtils.decrypt(deviceId,true);
        } catch (e: Exception) {
            "Exception"
        }
    }

    override fun setDeviceId(deviceId: String?) {
        val editor = sharedPreferences.edit()
        //        if (!TextUtils.isEmpty(deviceId))
//            deviceId = TypeConverterUtils.encrypt(deviceId,true);
        editor.putString(Constant.DEVICE_ID, deviceId)
        editor.apply()
    }

    //SharedPreferences sharedPreferences = getSharedPreferences(Constant.LOGIN_SETTINGS);
    val name: String?
        get() =//SharedPreferences sharedPreferences = getSharedPreferences(Constant.LOGIN_SETTINGS);
            sharedPreferences.getString(Constant.NAME, "No user found")

    override fun getEmail(): String {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.LOGIN_SETTINGS);
        return sharedPreferences.getString(Constant.EMAIL, "")!!
    }

    fun setSyllabus(
        preliLink: String?,
        writtenLink: String?,
        circularLink: String?,
        guide: String?,
        note: String?,
        videoId: String?
    ) {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        val editor = sharedPreferences.edit()
        editor.putString("preli", preliLink)
        editor.putString("written", writtenLink)
        editor.putString("circular", circularLink)
        editor.putString("note", note)
        editor.putString("guide", guide)
        editor.putString("videoid", videoId)
        editor.apply()
    }

    override fun setMathUnicodeList(unicodeList: MutableList<Unicode?>?) {
        try {
            val editor = sharedPreferences.edit()
            editor.putString("unicode", Gson().toJson(unicodeList))
            editor.apply()
        } catch (e: Exception) {
        }
    }

    override fun getUnicode(): MutableList<Unicode?>? {
        val value = sharedPreferences.getString("unicode", null) ?: return null
        return try {
            val type = object : TypeToken<List<Unicode?>?>() {}.type
            Gson().fromJson(value, type)
        } catch (e: Exception) {
            null
        }
    }//SharedPreferences sharedPreferences = getSharedPreferences("Upcoming_feature");

    //
    //    public void setUserInfo(UserInfo userInfo) {
    //        //SharedPreferences sharedPreferences = getSharedPreferences("Upcoming_feature");
    //
    //        if(userInfo!=null){
    //            SharedPreferences.Editor editor = sharedPreferences.edit();
    //
    //            String userInfoString = Utility.G
    //
    //            editor.putString(USER_INFO,upcoming);
    //            editor.apply();
    //        }
    //
    //    }
    val upcominng: String?
        get() =//SharedPreferences sharedPreferences = getSharedPreferences("Upcoming_feature");
            sharedPreferences.getString("upcoming", "")

    fun setUpcoming(upcoming: String?) {
        //SharedPreferences sharedPreferences = getSharedPreferences("Upcoming_feature");
        val editor = sharedPreferences.edit()
        editor.putString("upcoming", upcoming)
        editor.apply()
    }

    fun getGuideLink(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString(
            "guide",
            "http://bcsprostuti.com/files/preparation_guide.pdf"
        )
    }

    fun getPreliLink(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString(
            "preli",
            "http://bcsprostuti.com/files/preli_syllabus.pdf"
        )
    }

    fun getWrittenLink(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString(
            "written",
            "http://bcsprostuti.com/files/written_syllabus.pdf"
        )
    }

    fun getCircularLink(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString(
            "circular",
            "http://bcsprostuti.com/files/40th_circular.pdf"
        )
    }

    fun getNote(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString(
            "note",
            "[\"বিসিএস পরীক্ষা বা বাংলাদেশ সিভিল সার্ভিস পরীক্ষা হল দেশব্যাপী পরিচালিত একটি প্রতিযোগিতামূলক পরীক্ষার যা, বাংলাদেশ পাবলিক সার্ভিস কমিশন (বিপিএসসি) কর্তৃক বাংলাদেশ সিভিল সার্ভিসের বিসিএস (প্রশাসন), বিসিএস (কর), বিসিএস (পররাষ্ট্র) ও বিসিএস (পুলিশ) সহ ২৭ পদে কর্মী নিয়োগের  জন্য পরিচালিত হয়। এক্ষেত্রে প্রার্থীকে অবশ্যই নুন্যতম কোন অনার্স পাশের সার্টিফিকেট লাবে।\",    \"সাধারন শীক্ষার্থীর ক্ষেত্রে বয়স সীমা হলো ২১-৩০ বছর। এছাড়া বিভিন্ন কোটার জন্য সর্বোচ্চ বয়সসীমা হতে পারে ৩২ বছর। আর এ বয়স অবশ্যই এসএসসি পরীক্ষার সনদে উল্লেখিত বয়সের ভিত্তিতে কার্যকর করা হবে। বিসিএস  পরীক্ষা পর্যায়ক্রমে তিনটি ধাপে অনুষ্ঠিত হয়- প্রাথমিক পরীক্ষার (এমসিকিউ), লিখিত পরীক্ষা এবং  মৌখিক পরীক্ষা (ইন্টারভিউ)। পরীক্ষার বিজ্ঞপ্তি প্রকাশ থেকে চুড়ান্ত ফলাফল পর্যন্ত সমগ্র প্রক্রিয়া সম্পন্ন হতে ১.৫ থেকে ২ বছর সময় লাগে।\",    \"প্রীলিতে ২০০ মার্ক্সের জন্য মোট ২০০ টি প্রশ্ন থাকবে সময় ২ ঘন্টা। প্রতিটি সঠিক উত্তরের জন্য ১ নাম্বার যোগ হবে এবং প্রতিটি ভুল উত্তরের জন্য ১.৫ নাম্বার কাটা যাবে। লিখিত পরীক্ষার প্রতিটি ২০০ মার্ক্সের পরীক্ষার জন্য সময় মোট ৪ ঘন্টা এবং প্রতিটি ১০০ মার্ক্সের পরীক্ষার জন্য সময় মোট ৩ ঘন্টা। কোন পরীক্ষার্থী যদি ৩০% এর কম নম্বর পেয়ে থাকে তাহলে আর ওই বিষয়ে কোন নাম্বার যোগ হবে না। আর পরীক্ষার্থীকে পাস করার জন্য অবশ্যই তাকে ৫০% এর উপরে নাম্বার পেতে হবে। এমনকি ভাইভাতেও নুন্যতম কোয়ালিফায়িং নাম্বার হচ্ছে ৫০%।\"]"
        )
    }

    fun getVideoId(): String? {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getString("videoid", "E-22RRbE92c")
    }

    fun issGuest(): Boolean {
        //SharedPreferences sharedPreferences = getSharedPreferences("Syllabus");
        return sharedPreferences.getBoolean("is_guest", false)
    }

    fun setIsGuest(isGuest: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_guest", isGuest)
        editor.apply()
    }

    @Synchronized
    override fun getUser(): User? {
        var userData: String? = sharedPreferences.getString("user", null)
            ?: //Timber.d("User data null");
            return null

        //Timber.d(userData);
        val type = object : TypeToken<User?>() {}.type
        return try {
            userData = TypeConverterUtils.decrypt(userData, true)
            //Timber.d(userData);
            Gson().fromJson(userData, type)
        } catch (e: Exception) {
            //Timber.e(e);
            null
        }
    }

    override fun setUser(user: User?) {
        val editor = sharedPreferences.edit()
        if (user == null) {
            //Timber.d("User null");
            editor.putString("user", null)
            editor.apply()
            return
        }
        try {
            var userData = Gson().toJson(user)
            //Timber.d(userData);
            if (userData != null) {
                //Timber.d(userData);
                userData = TypeConverterUtils.encrypt(userData, true)
                editor.putString("user", userData)
                editor.apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setPoints(point: Int) {
        //SharedPreferences sharedPreferences = getSharedPreferences("points");
        val editor = sharedPreferences.edit()
        editor.putInt("point", point)
        editor.apply()
    }

    override fun getTotalQuestionCounterIndex(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences("Total_Question_Counter_Index");
        return sharedPreferences.getInt("total_question_counter_index", 1)
    }

    override fun setTotalQuestionCounterIndex(index: Int) {
        //SharedPreferences sharedPreferences = getSharedPreferences("Total_Question_Counter_Index");
        val editor = sharedPreferences.edit()
        editor.putInt("total_question_counter_index", index)
        editor.apply()
    }

    override fun setExamTypeCount(count: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("exam_type_count", count)
        editor.apply()
    }

    override fun getExamTypeCount(): Int {
        return sharedPreferences.getInt("exam_type_count", 0)
    }

    override fun setHighlightConfiguration(configuration: HighlightConfiguration?) {
        val editor = sharedPreferences.edit()
        val data = Gson().toJson(configuration)
        editor.putString("highlight_configuration", data)
        editor.apply()
    }

    override fun getHighlightConfiguration(): HighlightConfiguration {
        val value = sharedPreferences.getString("highlight_configuration", null)
            ?: return HighlightConfiguration()
        val type = object : TypeToken<HighlightConfiguration?>() {}.type
        return Gson().fromJson(value, type)
    }

    fun getQuestionCount(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences("question_count");
        return sharedPreferences.getInt("question", 0)
    }

    fun setQuestionCount(point: Int) {
        //SharedPreferences sharedPreferences = getSharedPreferences("question_count");
        val editor = sharedPreferences.edit()
        editor.putInt("question", point)
        editor.apply()
    }

    override fun setQuestionSetting(queNumber: Int) {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.QUESTION_SETTINGS);
        val editor = sharedPreferences.edit()
        /* editor.putInt(Constant.HOUR, hour);
        editor.putInt(Constant.MINUTE,min);*/editor.putInt(Constant.NUMBER_OF_QUESTION, queNumber)
        editor.apply()
    }

    override fun getHour(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.QUESTION_SETTINGS);
        return sharedPreferences.getInt(Constant.HOUR, 0)
    }

    fun getPoint(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences("points");
        return sharedPreferences.getInt("point", 0)
    }

    override fun getMinute(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.QUESTION_SETTINGS);
        return sharedPreferences.getInt(Constant.MINUTE, 30)
    }

    override fun getQuestionNumber(): Int {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.QUESTION_SETTINGS);
        return sharedPreferences.getInt(Constant.NUMBER_OF_QUESTION, 30)
    }

    @Synchronized
    override fun getSystemTimeDifference(): Long {
        if (timeDifference == null) {
            timeDifference = sharedPreferences.getLong(Constant.SystemTimeDiff, 0)
        }
        return timeDifference!!
    }

    override fun savedNoticeVersion(v: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("notice", v)
        editor.apply()
    }

    override fun getSavedNoticeVersion(): Int {
        //return -1;
        return sharedPreferences.getInt("notice", -1)
    }

    override fun setSystemTimeDifference(serverTime: Long) {
        //SharedPreferences sharedPreferences = getSharedPreferences(Constant.DAILY_TEST_DATA);
        val editor = sharedPreferences.edit()
        val localTime = Utility.getCurrentLocalTime()
        var diff = serverTime - localTime

        //Timber.i("1. System Time Difference %s", diff);
        if (Math.abs(diff) <= 5 * 60 * 1000) {
            diff = 0
        }

        //Timber.i("2. System Time Difference %s", diff);
        timeDifference = diff
        editor.putLong(Constant.SystemTimeDiff, diff)
        editor.apply()
    }

    companion object {
        private const val LEN_PREFIX = "Count_"
        private const val VAL_PREFIX = "IntValue_"
        private const val USER_INFO = "User_info"
    }

    init {
        sharedPreferences = context.getSharedPreferences("BCS Prostuti", Context.MODE_PRIVATE)
    }
}
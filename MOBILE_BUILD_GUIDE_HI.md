# सिर्फ मोबाइल से APK बनाने का तरीका

इस प्रोजेक्ट में `.github/workflows/android-build.yml` जोड़ा गया है। इससे GitHub Actions के क्लाउड कंप्यूटर पर APK build कराया जा सकता है; फोन पर Android Studio इंस्टॉल करने की जरूरत नहीं।

## फोन से कदम
1. ZIP को फोन में डाउनलोड करके Extract करें।
2. GitHub पर साइन इन करें और नया खाली repository बनाएं (उदाहरण: `fm-radio-ultra`)।
3. Repository में **Add file → Upload files** चुनकर Extract किए गए प्रोजेक्ट के सभी फोल्डर/फाइलें अपलोड करें। छिपा हुआ `.github` फोल्डर भी अपलोड होना चाहिए।
4. Commit changes करें।
5. Repository के **Actions** टैब में `Build FM Radio Ultra APK` चुनें।
6. **Run workflow** दबाएं।
7. Build सफल होने पर workflow run खोलें। नीचे **Artifacts** में `FM-Radio-Ultra-debug` डाउनलोड करें।
8. ZIP artifact को फोन में Extract करें; `app-debug.apk` इंस्टॉल करें। Android पूछे तो ब्राउज़र/फाइल मैनेजर को इस स्रोत से इंस्टॉल की अनुमति दें।

## ध्यान दें
- यह क्लाउड बिल्ड है; मोबाइल केवल ब्राउज़र/ऐप के जरिए GitHub नियंत्रित करेगा।
- यह Debug APK है, Play Store release नहीं।
- APK में अभी UI और read-only device diagnostic है। असली FM tuning/scan, Xiaomi private FM service integration, speaker routing, equalizer और recording अभी implemented/verified नहीं हैं।
- GitHub Actions की उपलब्धता repository/account की सीमाओं और GitHub की वर्तमान नीतियों पर निर्भर करती है।

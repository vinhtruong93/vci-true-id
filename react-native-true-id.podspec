require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-true-id"
  s.version      = "2.2.0"
  s.summary      = package["description"]
  s.description  = <<-DESC
                  Data Lap's PROPERTY
                   DESC
  s.homepage     = "vng.com.vn"
  s.license      = "VNG PRIVATE"
  # s.license    = { :type => "MIT", :file => "FILE_LICENSE" }
  s.authors      = { "dathv" => "dathv@vng.com.vn" }
  s.platforms    = { :ios => "11.0" }
  s.source       = { :path => "./" }

  s.source_files = "ios/**/*.{h,m,swift}"
  s.requires_arc = true
  
  s.vendored_frameworks = "Frameworks/TrueIDSDK.xcframework", "Frameworks/FaceTecSDK.xcframework", "Frameworks/FaceTrueIDSDK.xcframework", "Frameworks/OpenSSL.xcframework", "Frameworks/IDCardReaderLite.xcframework"
  s.xcconfig            = {
    'ALWAYS_EMBED_SWIFT_STANDARD_LIBRARIES' => 'YES'
  }
  
#  s.xcconfig = { 'FRAMEWORK_SEARCH_PATHS' => '$(inherited)' }
#  s.xcconfig = { 'FRAMEWORK_SEARCH_PATHS' => '"./node_modules/react-native-true-id/Frameworks"' }

  s.dependency "React"
  s.static_framework = true
  s.platform = :ios, '11.0'
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES','ENABLE_BITCODE' => 'NO', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'x86_64' }
  s.swift_version = '5.4'

  # ...
  # s.dependency "..."
  
  
end

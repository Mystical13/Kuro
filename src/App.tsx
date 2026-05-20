/**
 * @license
 * SPDX-License-Identifier: Apache-2.0
 */

import { Tv, Download, Github, Code2 } from 'lucide-react';

export default function App() {
  return (
    <div className="min-h-screen bg-[#0a0a0a] text-white flex flex-col items-center justify-center p-8 font-sans selection:bg-purple-500/30">
      <div className="max-w-3xl w-full flex flex-col items-center text-center space-y-10 animate-in fade-in slide-in-from-bottom-8 duration-700">
        
        {/* App Icon */}
        <div className="relative">
          <div className="absolute inset-0 bg-purple-600 blur-[80px] opacity-20 rounded-full" />
          <div className="w-32 h-32 bg-gradient-to-br from-gray-800 to-gray-900 border border-gray-700 rounded-3xl flex items-center justify-center relative shadow-2xl">
            <Tv size={56} className="text-purple-400" />
          </div>
        </div>
        
        <div className="space-y-4">
          <h1 className="text-5xl font-bold tracking-tight bg-gradient-to-br from-white to-gray-400 bg-clip-text text-transparent">
            KuroStream TV
          </h1>
          <p className="text-xl text-gray-400 max-w-xl mx-auto leading-relaxed">
            Your Android TV codebase is ready.
          </p>
        </div>

        {/* Info Banner */}
        <div className="bg-purple-900/20 border border-purple-500/30 rounded-2xl p-6 w-full text-left flex items-start space-x-4">
          <div className="p-2 bg-purple-500/20 rounded-lg">
            <Code2 className="text-purple-400" size={24} />
          </div>
          <div className="space-y-1">
            <h3 className="text-lg font-semibold text-purple-100">Android Kotlin Project Generated</h3>
            <p className="text-purple-200/70 leading-relaxed text-sm">
              This preview window is designed for running Web applications (React/Node), but you requested an Android TV app. The system has successfully generated the complete Kotlin, Jetpack Compose TV, and Gradle architecture in the workspace files.
            </p>
          </div>
        </div>

        {/* Instructions */}
        <div className="bg-gray-900/50 border border-gray-800 rounded-2xl p-8 w-full text-left space-y-6">
          <h2 className="text-xl font-semibold flex items-center gap-2">
            How to run on your TV
          </h2>
          
          <div className="grid sm:grid-cols-2 gap-6">
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center font-mono text-sm text-gray-400 border border-gray-700">1</div>
              <h4 className="font-medium">Export the Source</h4>
              <p className="text-sm text-gray-500">Click the share/export menu in AI Studio to download the project files as a ZIP or sync them to a GitHub repository.</p>
            </div>
            
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center font-mono text-sm text-gray-400 border border-gray-700">2</div>
              <h4 className="font-medium">Open in Android Studio</h4>
              <p className="text-sm text-gray-500">Open the downloaded folder in Android Studio. Gradle will automatically sync the dependencies (Media3, Compose TV, Hilt).</p>
            </div>

            <div className="space-y-3">
              <div className="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center font-mono text-sm text-gray-400 border border-gray-700">3</div>
              <h4 className="font-medium">Run on Emulator</h4>
              <p className="text-sm text-gray-500">Create an Android TV Virtual Device (API 34) and hit Run, or sideload the built APK directly to your physical TV.</p>
            </div>
            <div className="space-y-3">
              <div className="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center font-mono text-sm text-gray-400 border border-gray-700">4</div>
              <h4 className="font-medium">Add Logo and Banner</h4>
              <p className="text-sm text-gray-500">Download the images you uploaded and place the Logo as <code className="bg-gray-800 px-1 rounded">mipmap/ic_launcher.png</code> and Banner as <code className="bg-gray-800 px-1 rounded">drawable/banner.png</code> in Android Studio.</p>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

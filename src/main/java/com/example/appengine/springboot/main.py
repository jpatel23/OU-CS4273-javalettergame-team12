# Copyright 2021 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from flask import Flask, render_template, request
import google.auth
import pandas as pd
import random

app = Flask(__name__)
_, PROJECT_ID = google.auth.default()


def eng():
    return "lang_eng.csv"


def spn():
    return "lang_spn.csv"


def igb():
    return "lang_igb.csv"


def yrb():
    return "lang_yrb.csv"


def ijw():
    return "lang_ijw.csv"


def default():
    return "lang_eng.csv"


switcher = {
    1: eng,
    2: spn,
    3: igb,
    4: yrb,
    5: ijw
}


def getletters(lang):
    return pd.read_csv(switcher.get(lang, default)())


@app.route('/', methods=['GET', 'POST'])
def translate(gcf_request=None):
    try:

        # Flask Request object passed in for Cloud Functions
        # (use gcf_request for GCF but flask.request otherwise)
        local_request = gcf_request if gcf_request else request

        index = None

        letter = None

        language = None

        if local_request.method == 'POST':
            language = int(local_request.form['lang'].strip())

            letters = getletters(language)

            index = random.randint(0, len(letters.index) - 1)

            letter = letters.loc[index, ["UpperCase"]][0]

        # create context & render template
        context = {
            'language': language,
            'letter': letter,
            'index': index
        }

        return render_template('index.html', **context)
    except Exception as e:
        return e

if __name__ == '__main__':
    import os
    app.run(debug=True, threaded=True, host='0.0.0.0',
            port=int(os.environ.get('PORT', 8080)))

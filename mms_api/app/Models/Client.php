<?php

namespace App\Models;

use App\User;
use Illuminate\Database\Eloquent\Model;

class Client extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'profession','user_id','marchand_id',
        'employeur' ,
    ];

    public function user(){
        return $this->belongsTo('App\User');
    }

    public function marchand(){
        return $this->belongsTo('App\Models\Marchand','marchand_id');
    }

    public function documents(){
        return $this->morphMany('App\Models\Document','documenteable');
    }

    public function assures(){
        return $this->belongsToMany('App\Models\Assure','assurances')->using('App\Models\Assurance')->withPivot([
            'garantie','prime','duree','date_debut','date_echeance','date_effet', 'portefeuille',/*'numero_police_assurance' */
        ])->withTimestamps();
    }
}


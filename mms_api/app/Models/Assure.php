<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Assure extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id','profession',
        'employeur' ,
    ];

    public function user(){
        return $this->belongsTo('App\User','user_id');
    }

    public function beneficiares(){
        return $this->belongsToMany('App\Models\Beneficiaire','benefices')->using('App\Models\Benefice')->withPivot([
            'statut','taux',
        ])->withTimestamps('updated_at');
    }

    public function souscripteurs(){
        return $this->belongsToMany('App\Models\Client','assurances')->using('App\Models\Assurance')->withPivot([
            'garantie','duree','date_debut','date_echeance','date_effet','portefeuille','numero_police_assurance'
        ])->withTimestamps('updated_at');
    }

    public function documents(){
        return $this->morphMany('App\Models\Document','documenteable');
    }
}

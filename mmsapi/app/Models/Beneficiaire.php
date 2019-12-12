<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Beneficiaire extends Model
{

    public $incrementing=true;
    public $table='beneficiaires';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'id',
    ];

    public function user(){
        return $this->morphOne('App\User','userable');
    }

    public function benefices(){
        return $this->hasMany('App\Models\Benefice');
    }

    public function contrats(){
        return $this->belongsToMany('App\Models\Contrat','benefices','beneficiaire_id','contrat_id')->using('App\Models\Benefice')->withPivot([
            'statut','taux',
        ])->withTimestamps();
    }

}

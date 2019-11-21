<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Beneficiaire extends Model
{

    public $table='beneficiaires';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id',
    ];

    public function user(){
        return $this->belongsTo('App\User','user_id');
    }

    public function assures(){
        return $this->belongsToMany('App\Models\Assure','benefices')->using('App\Models\Benefice')->withPivot([
            'statut','taux',
        ])->withTimestamps('updated_at');
    }
}

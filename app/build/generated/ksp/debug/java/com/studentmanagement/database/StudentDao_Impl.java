package com.studentmanagement.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studentmanagement.model.Student;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `students` (`id`,`name`,`rollNumber`,`department`,`semester`,`phoneNumber`,`email`,`marks`,`attendance`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getRollNumber());
        statement.bindString(4, entity.getDepartment());
        statement.bindString(5, entity.getSemester());
        statement.bindString(6, entity.getPhoneNumber());
        statement.bindString(7, entity.getEmail());
        statement.bindDouble(8, entity.getMarks());
        statement.bindDouble(9, entity.getAttendance());
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `students` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `students` SET `id` = ?,`name` = ?,`rollNumber` = ?,`department` = ?,`semester` = ?,`phoneNumber` = ?,`email` = ?,`marks` = ?,`attendance` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getRollNumber());
        statement.bindString(4, entity.getDepartment());
        statement.bindString(5, entity.getSemester());
        statement.bindString(6, entity.getPhoneNumber());
        statement.bindString(7, entity.getEmail());
        statement.bindDouble(8, entity.getMarks());
        statement.bindDouble(9, entity.getAttendance());
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertStudent(final Student student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStudent.insert(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteStudent(final Student student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfStudent.handle(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStudent(final Student student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfStudent.handle(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Student>> getAllStudents() {
    final String _sql = "SELECT * FROM students ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getStudentById(final int id, final Continuation<? super Student> $completion) {
    final String _sql = "SELECT * FROM students WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<Student>> searchStudents(final String query) {
    final String _sql = "SELECT * FROM students WHERE name LIKE '%' || ? || '%' OR rollNumber LIKE '%' || ? || '%' OR department LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getStudentByRollNumber(final String rollNumber,
      final Continuation<? super Student> $completion) {
    final String _sql = "SELECT * FROM students WHERE rollNumber = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, rollNumber);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<Integer> getStudentCount() {
    final String _sql = "SELECT COUNT(*) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getAverageMarks() {
    final String _sql = "SELECT AVG(marks) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getHighestMarks() {
    final String _sql = "SELECT MAX(marks) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Double> getLowestMarks() {
    final String _sql = "SELECT MIN(marks) FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Student> getTopPerformer() {
    final String _sql = "SELECT * FROM students ORDER BY marks DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Student _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Student>> getAllStudentsSortedByName() {
    final String _sql = "SELECT * FROM students ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Student>> getAllStudentsSortedByMarks() {
    final String _sql = "SELECT * FROM students ORDER BY marks DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Student>> getAllStudentsSortedByRoll() {
    final String _sql = "SELECT * FROM students ORDER BY rollNumber ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"students"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllStudentsSync(final Continuation<? super List<Student>> $completion) {
    final String _sql = "SELECT * FROM students";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Student>>() {
      @Override
      @NonNull
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfRollNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "rollNumber");
          final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
          final int _cursorIndexOfSemester = CursorUtil.getColumnIndexOrThrow(_cursor, "semester");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "marks");
          final int _cursorIndexOfAttendance = CursorUtil.getColumnIndexOrThrow(_cursor, "attendance");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpRollNumber;
            _tmpRollNumber = _cursor.getString(_cursorIndexOfRollNumber);
            final String _tmpDepartment;
            _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
            final String _tmpSemester;
            _tmpSemester = _cursor.getString(_cursorIndexOfSemester);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final double _tmpMarks;
            _tmpMarks = _cursor.getDouble(_cursorIndexOfMarks);
            final double _tmpAttendance;
            _tmpAttendance = _cursor.getDouble(_cursorIndexOfAttendance);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new Student(_tmpId,_tmpName,_tmpRollNumber,_tmpDepartment,_tmpSemester,_tmpPhoneNumber,_tmpEmail,_tmpMarks,_tmpAttendance,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
